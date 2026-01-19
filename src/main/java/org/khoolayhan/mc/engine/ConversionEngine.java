package org.khoolayhan.mc.engine;

import java.io.File;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.engine.factory.StrategyFactory;
import org.khoolayhan.mc.utils.FileType;
import org.khoolayhan.mc.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Core engine responsible for orchestrating file conversion operations.
 * This class acts as the central component that coordinates the conversion process by:
 * <ul>
 *   <li>Extracting and validating file extensions</li>
 *   <li>Selecting appropriate conversion strategies via the Strategy Factory</li>
 *   <li>Executing the selected strategy</li>
 *   <li>Providing comprehensive logging throughout the process</li>
 * </ul>
 *
 * <p>The ConversionEngine uses the Strategy pattern to delegate actual conversion logic
 * to specialized strategy implementations, making the system extensible and maintainable.
 */
public class ConversionEngine {
    private static final Logger logger = LoggerFactory.getLogger(ConversionEngine.class);

    /**
     * Processes a file conversion request from input to output format.
     *
     * @param inputFile The source file.
     * @param outputFile The destination file.
     * @throws ConversionException if the conversion logic itself fails.
     * @throws UnsupportedFormatException if no strategy can be found for the conversion.
     */
    public void process(File inputFile, File outputFile)
            throws ConversionException, UnsupportedFormatException {
        logger.info("Starting conversion process for input: {}", inputFile.getName());

        FileType fromExtension =
                FileUtils.getExtension(inputFile)
                        .orElseThrow(
                                () ->
                                        new UnsupportedFormatException(
                                                "Input file has no valid extension."));

        FileType toExtension =
                FileUtils.getExtension(outputFile)
                        .orElseThrow(
                                () ->
                                        new UnsupportedFormatException(
                                                "Output file has no valid extension."));

        logger.debug("Determined conversion type: {} -> {}", fromExtension, toExtension);

        ConversionStrategy strategy = StrategyFactory.getStrategy(fromExtension, toExtension);
        logger.debug("Strategy selected: {}", strategy.getClass().getSimpleName());

        logger.info("Executing conversion...");
        strategy.convert(inputFile, outputFile);

        logger.info(
                "Conversion completed successfully with output file: {}",
                outputFile.getAbsolutePath());
    }
}
