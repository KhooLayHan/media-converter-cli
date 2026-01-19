package org.khoolayhan.mc.service;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Represents the result of a conversion operation. */
public class ConversionResult {
    private static final Logger logger = LoggerFactory.getLogger(ConversionResult.class);

    private final boolean success;
    private final File outputFile;
    private final String message;

    public ConversionResult(boolean success, File outputFile, String message) {
        this.success = success;
        this.outputFile = outputFile;
        this.message = message;

        logger.info("{}: {}", message, outputFile);
    }

    public boolean isSuccess() {
        return success;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public String getMessage() {
        return message;
    }
}
