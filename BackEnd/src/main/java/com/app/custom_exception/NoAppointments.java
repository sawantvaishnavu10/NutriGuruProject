package com.app.custom_exception;

public class NoAppointments extends Exception {

	public NoAppointments(String msg) {
		super(msg);
	}
}
