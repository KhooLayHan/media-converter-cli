package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.XmlToCsvStrategy;
import org.khoolayhan.mc.converter.impls.XmlToJsonStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;

public class XmlFactory implements ConverterFactory {
    @Override
    public ConversionStrategy createStrategy(String targetFormat)
            throws UnsupportedFormatException {
        return switch (targetFormat.toLowerCase()) {
            case "json" -> new XmlToJsonStrategy();
            case "csv" -> new XmlToCsvStrategy();
            default ->
                    throw new UnsupportedFormatException(
                            "Conversion from XML to " + targetFormat + " is not supported");
        };
    }
}
