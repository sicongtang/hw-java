package com.hyuki.dp.dp2.activeobject;

class ActivationQueue {
	private static final int MAX_METHOD_REQUEST = 100;
	private final MethodRequest[] requestQueue;
	private int tail; // 次にputRequestする場所
	private int head; // 次にtakeRequestする場所
	private int count; // MethodRequestの数

	public ActivationQueue() {
		this.requestQueue = new MethodRequest[MAX_METHOD_REQUEST];
		this.head = 0;
		this.tail = 0;
		this.count = 0;
	}

	public synchronized void putRequest(MethodRequest request) {
		while (count >= requestQueue.length) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		requestQueue[tail] = request;
		tail = (tail + 1) % requestQueue.length;
		count++;
		notifyAll();
	}

	public synchronized MethodRequest takeRequest() {
		while (count <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		MethodRequest request = requestQueue[head];
		head = (head + 1) % requestQueue.length;
		count--;
		notifyAll();
		return request;
	}
}
