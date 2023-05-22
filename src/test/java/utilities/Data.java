package utilities;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import testCases.Constants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Data {

    //reading from the XML file, by index
    public static String getData (String keyName, int index) throws ParserConfigurationException, IOException, SAXException, SAXException, SAXException {
        File configXmlFile = new File(Constants.CONFIG_FILE_LOCATION);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;

        dBuilder = dbFactory.newDocumentBuilder();

        Document doc = null;

        assert dBuilder != null;
        doc = dBuilder.parse(configXmlFile);

        if (doc != null) {
            doc.getDocumentElement().normalize();
        }
        assert doc != null;
        return doc.getElementsByTagName(keyName).item(index).getTextContent();
    }
}
