package org.khoolayhan.mc.converter.impls;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.models.User;
import org.khoolayhan.mc.engine.exceptions.ConversionException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.dataformat.csv.CsvMapper;
import tools.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonToCsvStrategy implements ConversionStrategy {
	@Override
	public void convert(File inputFile, File outputFile) throws ConversionException {
		try {
			// 1. Reads and deserialize JSON into a list of User POJOs
			ObjectMapper jsonMapper = new ObjectMapper();
			CollectionType listType = jsonMapper.getTypeFactory()
				.constructCollectionType(List.class, User.class);

			List<User> users = jsonMapper.readValue(inputFile, listType);

			// 2. Write and serialize the list of User POJOs to CSV
			CsvMapper csvMapper = new CsvMapper();

			// Builds a schema from the POJO properties and include a header row
			CsvSchema schema = csvMapper.schemaFor(User.class).withHeader();

			ObjectWriter writer = csvMapper.writer(schema);
			writer.writeValue(outputFile, users);
		} catch (IOException e) {
			throw new ConversionException("Failed to convert JSON to CSV", e);
		}
	}
}
