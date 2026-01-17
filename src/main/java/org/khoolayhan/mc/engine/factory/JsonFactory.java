package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.JsonToCsvStrategy;
import org.khoolayhan.mc.converter.impls.JsonToXmlStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;

public class JsonFactory implements ConverterFactory {
	@Override
	public ConversionStrategy createStrategy(String targetFormat) throws UnsupportedFormatException {
		return switch (targetFormat.toLowerCase()) {
			case "csv" -> new JsonToCsvStrategy();
			case "xml" -> new JsonToXmlStrategy();
			default -> throw new UnsupportedFormatException("Conversion from JSON to " + targetFormat + " is not supported");
		};
	}
}
