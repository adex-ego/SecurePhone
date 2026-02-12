#!/usr/bin/env bash
set -euo pipefail

# Simple build script for this repository.
# - builds Maven modules: client, serveur
# - builds Gradle module: shared (uses ./shared/gradlew if present, else system gradle)

SKIP_TESTS=false
MVN_CMD=mvn
GRADLE_CMD=gradle

usage() {
  cat <<EOF
Usage: $0 [--skip-tests] [--mvn MVN_CMD] [--gradle GRADLE_CMD]

Options:
  --skip-tests       Skip running tests during builds
  --mvn CMD          Maven command to use (default: mvn)
  --gradle CMD       Gradle command to use (default: gradle)
  -h, --help         Show this help
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --skip-tests) SKIP_TESTS=true; shift ;;
    --mvn) MVN_CMD="$2"; shift 2 ;;
    --gradle) GRADLE_CMD="$2"; shift 2 ;;
    -h|--help) usage; exit 0 ;;
    *) echo "Unknown arg: $1"; usage; exit 1 ;;
  esac
done

run_maven() {
  local modpath="$1"
  if [ -f "$modpath/pom.xml" ]; then
    local cmd
    if [ -x "$modpath/mvnw" ]; then
      cmd="$modpath/mvnw"
    else
      cmd="$MVN_CMD"
    fi
    local args="-f $modpath/pom.xml clean package"
    if [ "$SKIP_TESTS" = true ]; then
      args="$args -DskipTests"
    fi
    echo "==> Building $modpath with: $cmd $args"
    $cmd $args
  else
    echo "==> Skipping $modpath (no pom.xml)"
  fi
}

run_gradle() {
  local modpath="$1"
  if [ -f "$modpath/build.gradle" ] || [ -f "$modpath/build.gradle.kts" ]; then
    local cmd
    if [ -x "$modpath/gradlew" ]; then
      cmd="$modpath/gradlew"
      echo "==> Using wrapper: $cmd"
      if [ "$SKIP_TESTS" = true ]; then
        echo "==> Building $modpath with tests skipped"
        (cd "$modpath" && $cmd build -x test)
      else
        (cd "$modpath" && $cmd build)
      fi
    elif command -v "$GRADLE_CMD" >/dev/null 2>&1; then
      cmd="$GRADLE_CMD"
      # Determine whether the project defines a 'test' task before excluding it
      local args="-p $modpath build"
      if [ "$SKIP_TESTS" = true ]; then
        if $cmd -p "$modpath" tasks --all 2>/dev/null | grep -qE "(^|\s)test(\s|$)"; then
          args="$args -x test"
        else
          echo "==> No 'test' task found in $modpath; not excluding tests"
        fi
      fi
      echo "==> Building $modpath with: $cmd $args"
      $cmd $args
    else
      echo "==> No gradle wrapper or gradle command found for $modpath - skipping"
    fi
  else
    echo "==> Skipping $modpath (no build.gradle)"
  fi
}

echo "Starting build at $(date)"

# Build Maven modules
run_maven client
run_maven serveur

# Build Gradle module
run_gradle shared

echo "Build finished at $(date)"

echo "Tip: make the script executable with: chmod +x run.sh"
