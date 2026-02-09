package com.securephone.client.audio;

import java.util.ArrayDeque;
import java.util.Queue;

public class AudioBuffer {

	private final Queue<byte[]> queue;
	private final int maxSize;

	public AudioBuffer(int maxSize) {
		this.maxSize = Math.max(1, maxSize);
		this.queue = new ArrayDeque<>(this.maxSize);
	}

	public synchronized void add(byte[] data) {
		if (data == null) {
			return;
		}
		if (queue.size() >= maxSize) {
			queue.poll();
		}
		queue.offer(data);
	}

	public synchronized byte[] poll() {
		return queue.poll();
	}

	public synchronized int size() {
		return queue.size();
	}

	public synchronized void clear() {
		queue.clear();
	}
}
