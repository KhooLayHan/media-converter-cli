package org.khoolayhan.mc.converter;

import java.io.File;

import org.khoolayhan.mc.engine.exceptions.ConversionException;

/**
 * The central Strategy interface for all conversion operations. Each implementation will know how
 * to perform one specific type of conversion.
 */
public interface ConversionStrategy {
    /**
     * General convert method to read from and output file conversion.
     *
     * @param inputFile the source file to read data from; must not be {@code null}
     * @param outputFile the target file to write converted data to; must not be {@code null}
     * @throws ConversionException if the conversion fails due to I/O, parsing, or other errors
     */
    void convert(File inputFile, File outputFile) throws ConversionException;
}
