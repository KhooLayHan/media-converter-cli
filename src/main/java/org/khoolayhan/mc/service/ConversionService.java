package org.khoolayhan.mc.service;

import java.io.File;

import org.khoolayhan.mc.engine.ConversionEngine;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service responsible for orchestrating file conversion operations. This class contains the core
 * business logic separated from CLI concerns.
 */
public class ConversionService {
    private static final Logger logger = LoggerFactory.getLogger(ConversionService.class);

    private final ConversionEngine conversionEngine;

    public ConversionService(ConversionEngine conversionEngine) {
        this.conversionEngine = conversionEngine;
    }

    /**
     * Validates that the input file exists and is a valid file.
     *
     * @param inputFile the file to validate
     * @throws IllegalArgumentException if file is invalid
     */
    public void validateInputFile(File inputFile) {
        if (inputFile == null) {
            throw new IllegalArgumentException("Input file cannot be null");
        }
        if (!inputFile.exists()) {
            throw new IllegalArgumentException(
                    "Input file does not exist: " + inputFile.getAbsolutePath());
        }
        if (!inputFile.isFile()) {
            throw new IllegalArgumentException(
                    "Input path is not a file: " + inputFile.getAbsolutePath());
        }

        // Log technical details that users don't need to see
        logger.debug(
                "Input file validation passed. Size: {} bytes, Can read: {}",
                inputFile.length(),
                inputFile.canRead());
    }

    /**
     * Converts a file from one format to another.
     *
     * @param inputFile the source file
     * @param outputFile the destination file
     * @return ConversionResult containing status and details
     * @throws ConversionException if conversion fails
     * @throws UnsupportedFormatException if format is not supported
     */
    public ConversionResult convert(File inputFile, File outputFile)
            throws ConversionException, UnsupportedFormatException {
        logger.info(
                "Starting conversion: {} -> {}",
                inputFile.getAbsolutePath(),
                outputFile.getAbsolutePath());

        validateInputFile(inputFile);

        conversionEngine.process(inputFile, outputFile);

        return new ConversionResult(true, outputFile, "Conversion completed successfully.");
    }
}
