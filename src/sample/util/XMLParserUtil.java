package sample.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParserUtil {

    private Document xmlDocument;

    public XMLParserUtil(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        if (xmlFile == null) {
            throw new NullPointerException("XML file should not be null");
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document parsedDocument = builder.parse(xmlFile);

        setXmlDocument(parsedDocument);

    }

    public Document getXmlDocument() {
        return xmlDocument;
    }

    private void setXmlDocument (Document newDocument) {
        xmlDocument = newDocument;
    }
}
