package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.ConversionStrategy;
import org.khoolayhan.mc.converter.facade.JacksonFacade;
import org.khoolayhan.mc.converter.models.User;
import org.khoolayhan.mc.engine.exceptions.ConversionException;

import tools.jackson.core.JacksonException;

public class XmlToJsonStrategy implements ConversionStrategy {
    private final JacksonFacade jacksonFacade;

    public XmlToJsonStrategy() {
        this.jacksonFacade = new JacksonFacade();
    }

    @Override
    public void convert(File inputFile, File outputFile) throws ConversionException {
        try {
            List<User> users = jacksonFacade.readXml(inputFile, User.class);
            jacksonFacade.writeJson(outputFile, users);
        } catch (JacksonException e) {
            throw new ConversionException("Failed to convert XML to JSON", e);
        }
    }
}
