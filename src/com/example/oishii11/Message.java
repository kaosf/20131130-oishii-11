package com.example.oishii11;

import java.util.Random;

public class Message {
	int id;
	String body;

	public Message(String body) {
		this.id = new Random(System.currentTimeMillis()).nextInt();
		this.body = body;
	}

	public Message(int id, String body) {
		this.id = id;
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public String getBody() {
		return body;
	}
}
