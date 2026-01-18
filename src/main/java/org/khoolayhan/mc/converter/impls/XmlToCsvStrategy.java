package org.khoolayhan.mc.converter.impls;

import java.io.File;
import java.util.List;

import org.khoolayhan.mc.converter.models.User;

public class XmlToCsvStrategy extends AbstractConversionStrategy {

    @Override
    protected void convertInternal(File inputFile, File outputFile) {
        List<User> users = jacksonFacade.readXml(inputFile, User.class);
        jacksonFacade.writeCsv(outputFile, users);
    }
}
