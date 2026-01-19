package org.khoolayhan.mc.constants;

/**
 * Defines standard exit codes for the Media Converter application. Following Unix convention where
 * 0 indicates success and non-zero indicates various failures.
 */
public final class ExitCodes {
    public static final int EXIT_SUCCESS = 0;
    public static final int EXIT_INVALID_INPUT = 1;
    public static final int EXIT_CONVERSION_FAILED = 2;
    public static final int EXIT_UNEXPECTED_ERROR = 3;

    private ExitCodes() {
        throw new AssertionError("Cannot instantiate constants class");
    }
}
