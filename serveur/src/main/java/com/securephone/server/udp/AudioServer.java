package com.securephone.server.udp;

public class AudioServer {

    private Thread thread;

    public AudioServer() {
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(() -> {
                // minimal stub: keep thread alive
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ignored) {
                }
            }, "AudioServer-Stub");
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
        }
    }
}
