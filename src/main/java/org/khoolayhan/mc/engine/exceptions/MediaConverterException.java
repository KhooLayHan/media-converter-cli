package org.khoolayhan.mc.engine.exceptions;

/**
 * Base exception class for all Media Converter related exceptions.
 * This provides a common parent for all custom exceptions in the application,
 * making exception handling more structured and maintainable.
 */
public class MediaConverterException extends Exception {
	public MediaConverterException(String message) {
		super(message);
	}

	public MediaConverterException(String message, Throwable cause) {
		super(message, cause);
	}

	public MediaConverterException(Throwable cause) {
		super(cause);
	}
}
