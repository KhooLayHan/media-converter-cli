package org.khoolayhan.mc.converter.impls;

import java.io.File;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.facade.JacksonFacade;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tools.jackson.core.JacksonException;

/**
 * This abstract class defines the skeleton of the conversion operation. It handles common tasks
 * like logging, error handling, and facade creation. Subclasses only need to implement the specific
 * 'convertInternal' logic.
 */
public abstract class AbstractConversionStrategy implements ConversionStrategy {
    private static final Logger logger = LoggerFactory.getLogger(AbstractConversionStrategy.class);

    protected final JacksonFacade jacksonFacade;

    protected AbstractConversionStrategy() {
        this.jacksonFacade = new JacksonFacade();
    }
    ;

    @Override
    public final void convert(File inputFile, File outputFile) throws ConversionException {
        if (!inputFile.exists()) {
            throw new ConversionException(
                    "Input file does not exist: " + inputFile.getName(), null);
        }

        logger.debug("Converting {} to {}", inputFile.getName(), outputFile.getName());

        try {
            convertInternal(inputFile, outputFile);
        } catch (JacksonException e) {
            logger.error(
                    "Error during conversion in {}: {}",
                    this.getClass().getSimpleName(),
                    e.getMessage());
            throw new ConversionException("Conversion failed: " + e.getMessage(), e);
        }
    }

    protected abstract void convertInternal(File input, File output);
}
