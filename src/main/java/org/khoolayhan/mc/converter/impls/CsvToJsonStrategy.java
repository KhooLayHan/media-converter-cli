package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.models.User;
import org.khoolayhan.mc.engine.exceptions.ConversionException;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.MappingIterator;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.csv.CsvMapper;
import tools.jackson.dataformat.csv.CsvSchema;

public class CsvToJsonStrategy implements ConversionStrategy {
    @Override
    public void convert(File inputFile, File outputFile) throws ConversionException {
        try {
            // 1. Reads CSV into a List<User>
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();

			// MappingIterator is used to read row by row
			try (
				MappingIterator<User> iterator = csvMapper.readerFor(User.class).with(schema).readValues(inputFile);
			) {
				List<User> users = iterator.readAll();

				// 2. Write List<User> to JSON
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(outputFile, users);
			}
        } catch (JacksonException e) {
            throw new ConversionException("Failed to convert CSV to JSON", e);
        }
    }
}
