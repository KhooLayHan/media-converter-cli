package org.khoolayhan.mc.cli;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import com.github.lalyos.jfiglet.FigletFont;

import org.khoolayhan.mc.engine.ConversionEngine;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
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

    private final ConversionEngine conversionEngine;

	public MediaConverter() {
		this.conversionEngine = new ConversionEngine();
	}

	public MediaConverter(File inputFile, File outputFile, ConversionEngine conversionEngine) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		this.conversionEngine = conversionEngine;
    }

    @Override
    public Integer call() {
        if (!inputFile.exists() || !inputFile.isFile()) {
            logger.error("Input file does not exist: {}", inputFile.getAbsolutePath());
            System.err.println("❌ Error: Input file not found: " + inputFile.getAbsolutePath());
            return 1; // Exit code 1 indicates failure
        }

        try {
            // 2. Delegate to the engine
            System.out.println("⏳ Starting conversion...");
            conversionEngine.process(inputFile, outputFile);
            System.out.println("✅ Success! Output created at: " + outputFile.getAbsolutePath());
            return 0; // Exit code 0 indicates success
        } catch (UnsupportedFormatException e) {
            // Handle expected errors (User asked for something we can't do)
            logger.warn("Unsupported format requested: {}", e.getMessage());
            System.err.println("❌ Error: " + e.getMessage());
            return 1;

        } catch (ConversionException e) {
            // Handle runtime errors during conversion (Corrupt files, IO errors)
            logger.error("Conversion failed", e);
            System.err.println("❌ Error during conversion: " + e.getMessage());
            System.err.println("   (Check the logs for technical details)");
            return 2;

        } catch (Exception e) {
            // Handle unexpected crashes
            logger.error("Unexpected error", e);
            System.err.println("❌ An unexpected error occurred: " + e.getMessage());
            return 3;
        }
    }

    public static void main(String[] args) {
        printBanner();

        // 2. Execute the CLI logic using Picocli
        int exitCode = new CommandLine(new MediaConverter()).execute(args);
        System.exit(exitCode);
    }

    private static void printBanner() {
        // 1. Print the startup banner
        try {
            String asciiArt = FigletFont.convertOneLine("Media Converter");
            logger.info("{}", asciiArt);
        } catch (IOException e) {
            System.out.println("--- Media Converter ---");
        }
    }
}
