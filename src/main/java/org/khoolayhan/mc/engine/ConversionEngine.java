package org.khoolayhan.mc.engine;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;
import org.khoolayhan.mc.engine.factory.StrategyFactory;
import org.khoolayhan.mc.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ConversionEngine {
	private static final Logger logger = LoggerFactory.getLogger(ConversionEngine.class);

	/**
	 * Processes a conversion request.
	 *
	 * @param inputFile  The source file.
	 * @param outputFile The destination file.
	 * @throws ConversionException        if the conversion logic itself fails.
	 * @throws UnsupportedFormatException if no strategy can be found for the conversion.
	 */
	public void process(File inputFile, File outputFile) throws ConversionException, UnsupportedFormatException {
		logger.info("Starting conversion process for input: {}", inputFile.getName());

		String fromExtension = FileUtils.getExtension(inputFile)
			.orElseThrow(() -> new UnsupportedFormatException("Input file has no extension."));

		String toExtension = FileUtils.getExtension(outputFile)
			.orElseThrow(() -> new UnsupportedFormatException("Output file has no extension."));

		logger.debug("Determined conversion type: {} -> {}", fromExtension, toExtension);

		ConversionStrategy strategy = StrategyFactory.getStrategy(fromExtension, toExtension);
		logger.debug("Strategy selected: {}", strategy.getClass().getSimpleName());

		logger.info("Executing conversion...");
		strategy.convert(inputFile, outputFile);

		logger.info("Conversion completed successfully with output file: {}", outputFile.getAbsolutePath());
	}
}
