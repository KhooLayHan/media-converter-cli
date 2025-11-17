package org.khoolayhan.mc.cli;

import java.io.File;
import java.io.IOException;

import com.github.lalyos.jfiglet.FigletFont;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;

@CommandLine.Command(
    name = "converter",
    mixinStandardHelpOptions = true,
    version = "Media Converter 1.0",
    description = "Converts media, documents, and data files from one format to another.")
public class MediaConverter implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(MediaConverter.class);

  @CommandLine.Option(
      names = {"-i", "--input"},
      required = true,
      description = "The input file to convert.")
  private File inputFile;

  @CommandLine.Option(
      names = {"-o", "--output"},
      required = true,
      description = "The output file.")
  private File outputFile;

  @Override
  public void run() {
    logger.info("Initializing conversion process...");
    logger.debug("Input: {}", inputFile.getAbsolutePath());
    logger.debug("Output: {}", outputFile.getAbsolutePath());

    // TODO:
    // 1. Get file extensions from inputFile and outputFile.
    // 2. Use StrategyFactory.getStrategy(inputExt, outputExt) to get the right converter.
    // 3. Call the strategy's convert() method.
    // 4. Handle any exceptions and print user-friendly error messages.

    logger.info("Conversion completed successfully.");
  }

  public static void main(String[] args) {
    // 1. Print the startup banner
    try {
      String asciiArt = FigletFont.convertOneLine("Media Converter");
      logger.info("{}", asciiArt);
    } catch (IOException e) {
      System.out.println("--- Media Converter ---");
    }
    //
    //        // 2. Execute the CLI logic using Picocli
    //        int exitCode = new CommandLine(new MediaConverter()).execute(args);
    //        System.exit(exitCode);
  }
}
