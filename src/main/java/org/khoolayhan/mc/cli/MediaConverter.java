package org.khoolayhan.mc.cli;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import com.github.lalyos.jfiglet.FigletFont;

import org.khoolayhan.mc.constants.ExitCodes;
import org.khoolayhan.mc.engine.ConversionEngine;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import org.khoolayhan.mc.engine.exceptions.MediaConverterException;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.service.ConversionResult;
import org.khoolayhan.mc.service.ConversionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;

@CommandLine.Command(
        name = "converter",
        mixinStandardHelpOptions = true,
        version = "Media Converter 1.0",
        description = "Converts media, documents, and data files from one format to another.")
public class MediaConverter implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(MediaConverter.class);

    @CommandLine.Option(
            names = {"-i", "--input"},
            required = true,
            description = "The input file to convert.")
    @SuppressWarnings("NullAway.Init")
    private File inputFile;

    @CommandLine.Option(
            names = {"-o", "--output"},
            required = true,
            description = "The output file.")
    @SuppressWarnings("NullAway.Init")
    private File outputFile;

    private final ConversionService conversionService;
    private final UserFeedback feedback;

    public MediaConverter() {
        this(new ConversionService(new ConversionEngine()), new UserFeedback(logger));
    }

    public MediaConverter(ConversionService conversionService, UserFeedback feedback) {
        this.conversionService = conversionService;
        this.feedback = feedback;
    }

    @Override
    public Integer call() {
        long startTime = System.currentTimeMillis();
        logger.debug("Working directory: {}", System.getProperty("user.dir"));

        try {
            feedback.showProgress("Starting conversion...");
            feedback.showInfo("Input: " + inputFile.getName());
            feedback.showInfo("Output: " + outputFile.getName());

            ConversionResult result = conversionService.convert(inputFile, outputFile);

            // Success handling
            if (result.isSuccess()) {
                feedback.showSuccess(
                        "Output created at: " + result.getOutputFile().getAbsolutePath());
                logger.info("--- Conversion Request Completed Successfully ---");

                return ExitCodes.EXIT_SUCCESS;
            } else {
                feedback.showError(result.getMessage());
                logger.warn("Conversion returned unsuccessful result: {}", result.getMessage());

                return ExitCodes.EXIT_CONVERSION_FAILED;
            }
        } catch (IllegalArgumentException e) {
            feedback.showError(e.getMessage() != null ? e.getMessage() : "Invalid input");

            logger.error("Input validation failed: {}", e.getMessage());
            logger.debug("Validation failure details", e);

            return ExitCodes.EXIT_INVALID_INPUT;
        } catch (MediaConverterException e) {
			switch (e) {
				case UnsupportedFormatException ex -> {
					feedback.showError(e.getMessage() != null ? e.getMessage() : "Unsupported format");
					// feedback.showInfo("Use --help to see supported formats");

					logger.warn("Unsupported format requested: {}", e.getMessage());

					return ExitCodes.EXIT_INVALID_INPUT;
				}
				case ConversionException ex -> {
					feedback.showError(
							"Conversion failed: " + e.getMessage(), "Check the logs for technical details");
					logger.error("Conversion exception occurred", e);

					return ExitCodes.EXIT_CONVERSION_FAILED;
				}
				default -> {
					feedback.showDetailedError("An unexpected error occurred", e);

					logger.error("Unexpected exception in conversion process", e);
					logger.error("Input file: {}, Output file: {}", inputFile, outputFile);

					return ExitCodes.EXIT_UNEXPECTED_ERROR;
				}
			}
        } finally {
            long totalDuration = System.currentTimeMillis() - startTime;
            logger.info("Total execution time: {}ms", totalDuration);
        }
    }

    public static void main(String[] args) {
        UserFeedback feedback = new UserFeedback(logger);

        printBanner(feedback);

        logger.info("--- Media Converter CLI started ---");
        logger.debug("Command line arguments: {}", String.join(" ", args));

        int exitCode = new CommandLine(new MediaConverter()).execute(args);

        logger.info("Application exiting with code: {}", exitCode);
        System.exit(exitCode);
    }

    private static void printBanner(UserFeedback feedback) {
        try {
            String asciiArt = FigletFont.convertOneLine("Media Converter");
            feedback.showPlain(asciiArt);
        } catch (IOException e) {
            feedback.showPlain("--- Media Converter ---");
            logger.debug("Failed to load FigletFont, using simple banner", e);
        }
    }
}
