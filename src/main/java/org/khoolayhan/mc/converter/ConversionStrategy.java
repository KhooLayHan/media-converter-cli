package org.khoolayhan.mc.converter;

import java.io.File;

import org.khoolayhan.mc.engine.exceptions.ConversionException;

/**
 * The central Strategy interface for all conversion operations. Each implementation will know how
 * to perform one specific type of conversion.
 */
public interface ConversionStrategy {
  void convert(File inputFile, File outputFile) throws ConversionException;
}
