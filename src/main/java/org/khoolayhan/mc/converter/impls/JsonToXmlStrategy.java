package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.models.User;

/**
 * Strategy implementation for converting JSON files to XML format. Uses Jackson libraries for
 * deserialization and XML writing.
 */
public class JsonToXmlStrategy extends AbstractConversionStrategy {
    @Override
    protected void convertInternal(File inputFile, File outputFile) {
        List<User> users = jacksonFacade.readJson(inputFile, User.class);
        jacksonFacade.writeXml(outputFile, users);
    }
}
