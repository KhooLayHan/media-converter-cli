package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.io.FileNotFoundException;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.facade.JacksonFacade;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.core.JacksonException;

/**
 * Abstract base class implementing the Template Method pattern for file conversion strategies. This
 * abstract class defines the skeleton of the conversion operation. It handles common tasks like
 * logging, error handling, and facade creation. Subclasses only need to implement the specific
 * 'convertInternal' logic.
 */
public abstract class AbstractConversionStrategy implements ConversionStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConversionStrategy.class);

    protected final JacksonFacade jacksonFacade;

    protected AbstractConversionStrategy() {
        this.jacksonFacade = new JacksonFacade();
    }

    /**
     * Template method that orchestrates the conversion process. This method is final to prevent
     * subclasses from overriding the conversion workflow. It performs the following steps:
     *
     * <ol>
     *   <li>Validates that the input file exists
     *   <li>Logs the conversion attempt
     *   <li>Delegates to {@link #convertInternal(File, File)} for actual conversion
     *   <li>Catches and wraps Jackson exceptions in ConversionException
     * </ol>
     *
     * @param inputFile the source file to be converted (must exist)
     * @param outputFile the destination file where converted content will be written
     * @throws ConversionException if the input file does not exist, or if the conversion process
     *     fails for any reason (wraps underlying exceptions)
     */
    @Override
    public final void convert(File inputFile, File outputFile) throws ConversionException {
        if (!inputFile.exists()) {
            throw new ConversionException(
                    "Input file does not exist: " + inputFile.getName(),
                    new FileNotFoundException(inputFile.getPath()));
        }

        logger.debug("Converting {} to {}", inputFile.getName(), outputFile.getName());

        try {
            convertInternal(inputFile, outputFile);
        } catch (JacksonException e) {
            logger.error(
                    "Error during conversion in {}: {}",
                    this.getClass().getSimpleName(),
                    e.getMessage());
        }
    }

    /**
     * Performs the actual conversion logic specific to each strategy. This method is called by the
     * template method {@link #convert(File, File)} after all validation and setup is complete.
     * Subclasses must implement this method to provide their specific conversion behavior.
     *
     * @param input the validated source file to convert
     * @param output the destination file for the conversion result
     * @throws JacksonException if JSON/XML/CSV processing fails (will be caught and wrapped by the
     *     template method)
     */
    protected abstract void convertInternal(File input, File output);
}
