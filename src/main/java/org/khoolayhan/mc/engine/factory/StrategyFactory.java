package org.khoolayhan.mc.engine.factory;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.impls.CsvToJsonStrategy;
import org.khoolayhan.mc.converter.impls.JsonToCsvStrategy;
import org.khoolayhan.mc.converter.impls.JsonToXmlStrategy;
import org.khoolayhan.mc.engine.exceptions.UnsupportedFormatException;

import java.util.HashMap;
import java.util.Map;

/**
 * The entry point for the creation logic.
 * This class acts as a registry for the specific ConverterFactories.
 */
public final class StrategyFactory {
	private static final Map<String, ConverterFactory> FACTORY_MAP = new HashMap<>();

	static {
		FACTORY_MAP.put("json", new JsonFactory());
		FACTORY_MAP.put("csv", new CsvFactory());
		FACTORY_MAP.put("xml", new XmlFactory());
	}

    private StrategyFactory() {}

    /**
     *
	 * Resolves the correct strategy using the Factory Method pattern.
     *
     * @param fromExtension The source file extension (e.g., "json").
     * @param toExtension The target file extension (e.g., "csv").
     * @return An instance of the required ConversionStrategy.
     * @throws UnsupportedFormatException if the requested conversion is not supported.
     */
    public static ConversionStrategy getStrategy(String fromExtension, String toExtension)
            throws UnsupportedFormatException {

		ConverterFactory factory = FACTORY_MAP.get(fromExtension);
		if (factory == null) {
			throw new UnsupportedFormatException("Input format " + fromExtension + " is not supported");
		}

		return factory.createStrategy(toExtension);
    }
}
