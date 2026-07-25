package org.javarush.module2Task.errors;

public class AnimalIslandException extends RuntimeException {
	public AnimalIslandException(String message) {
		super(message);
	}
	public AnimalIslandException(Exception e) {
		super(e);
	}
}
