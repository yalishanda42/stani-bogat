package sample;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParserUtil {

    private Document xmlDocument;

    public XMLParserUtil(String xmlFilename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilename));
    }

    public Document getXmlDocument() {
        return xmlDocument;
    }

    private void setXmlDocument (Document newDocument) {
        xmlDocument = newDocument;
    }
}
