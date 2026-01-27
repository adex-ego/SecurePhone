package com.securephone.server.udp;

public class VideoServer {

    private Thread thread;

    public VideoServer() {
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
            }, "VideoServer-Stub");
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
