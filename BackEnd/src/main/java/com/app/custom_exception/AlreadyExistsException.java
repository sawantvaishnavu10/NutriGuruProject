package com.app.custom_exception;

public class AlreadyExistsException extends Exception{

	public AlreadyExistsException(String msg) {
		super(msg);
	}
}
