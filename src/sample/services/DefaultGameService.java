package sample.services;

import org.xml.sax.SAXException;
import sample.services.interfaces.GameService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DefaultGameService implements GameService {
    private final DefaultDatabaseService databaseService;

    DefaultGameService() throws ParserConfigurationException, SAXException, IOException {
        databaseService = new DefaultDatabaseService();
    }

    // TODO: implement interface
}
