package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.models.User;
import org.khoolayhan.mc.engine.exceptions.ConversionException;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.CollectionType;
import tools.jackson.dataformat.xml.XmlMapper;

public class JsonToXmlStrategy implements ConversionStrategy {
    @Override
    public void convert(File inputFile, File outputFile) throws ConversionException {
        try {
            // 1. Read JSON
            ObjectMapper mapper = new ObjectMapper();
            CollectionType listType =
                    mapper.getTypeFactory().constructCollectionType(List.class, User.class);
            List<User> users = mapper.readValue(inputFile, listType);

            // 2. Write XML
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.writeValue(outputFile, users);
        } catch (JacksonException e) {
            throw new ConversionException("Failed to convert JSON to XML", e);
        }
    }
}
