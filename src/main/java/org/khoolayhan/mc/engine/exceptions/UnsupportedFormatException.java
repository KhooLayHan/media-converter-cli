package org.khoolayhan.mc.engine.exceptions;

/**
 * Exception thrown when an unsupported file format is encountered. This exception indicates that
 * the requested conversion involves a file format that is not supported by the Media Converter
 * application.
 */
public class UnsupportedFormatException extends MediaConverterException {
    public UnsupportedFormatException(String message) {
        super(message);
    }

    public UnsupportedFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
