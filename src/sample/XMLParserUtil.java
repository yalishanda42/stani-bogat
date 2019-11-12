package sample;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XMLParserUtil {

    private Document xmlDocument;

    public XMLParserUtil(String xmlFilename) throws ParserConfigurationException, IOException, SAXException, XMLNotValidException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document parsedDocument = builder.parse(new File(xmlFilename));
        setXmlDocument(parsedDocument);
    }

    private boolean xmlDocumentIsValid(Document document, String schemaFilename) {
        Schema schema;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(new File(schemaFilename));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        Validator validator = schema.newValidator();
        try {
            validator.validate(new DOMSource(document));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Document getXmlDocument() {
        return xmlDocument;
    }

    private void setXmlDocument (Document newDocument) {
        xmlDocument = newDocument;
    }

    private static class XMLNotValidException extends Exception {
        XMLNotValidException(String xmlFilename) {
            System.out.println(String.format("XML file '%s' not valid!", xmlFilename));
        }
    }
}
