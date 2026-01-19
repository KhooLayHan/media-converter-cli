package org.khoolayhan.mc.engine.exceptions;

/**
 * Exception thrown when a media conversion operation fails.
 * This exception indicates that the conversion process encountered an error
 * during execution, such as file processing issues or codec problems.
 */
public class ConversionException extends MediaConverterException {
	public ConversionException(String message) {
		super(message);
	}

	public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
