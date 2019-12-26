package sample.services;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.services.interfaces.DatabaseService;
import sample.util.XMLParserUtil;
import sample.models.Answer;
import sample.models.PrizeCategory;
import sample.models.Question;
import sample.models.QuestionsContainer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DefaultDatabaseService implements DatabaseService {

    private static final String XML_FILENAME = "src/sample/database.xml";

    private final XMLParserUtil parser;

    DefaultDatabaseService() throws IOException, SAXException, ParserConfigurationException {
        parser = new XMLParserUtil(new File(XML_FILENAME));
    }

    @Override
    public QuestionsContainer fetchAllQuestionsFromDatabase() {
        QuestionsContainer container = new QuestionsContainer();
        Document document = parser.getXmlDocument();

        NodeList difficultyNodes = document.getElementsByTagName("difficulty");
        for (int i = 0; i < difficultyNodes.getLength(); i++) {
            Node node = difficultyNodes.item(i);
            String prizeString = node.getAttributes().getNamedItem("prize").getNodeValue();
            PrizeCategory prize = PrizeCategory.valueOf("LVL_" + prizeString);

            NodeList questionNodes = node.getChildNodes();
            for (int j = 0; j < questionNodes.getLength(); j++) {
                Node questionNode = questionNodes.item(j);
                if (questionNode.getNodeType() == Node.ELEMENT_NODE) {
                    String questionText = questionNode.getAttributes().getNamedItem("text").getNodeValue();

                    ArrayList<Answer> answers = new ArrayList<>();
                    NodeList answerNodes = questionNode.getChildNodes();
                    for (int k = 0; k < answerNodes.getLength(); k++) {
                        Node answerNode = answerNodes.item(k);
                        String answerText = answerNode.getTextContent();
                        if (answerText == null) {
                            continue;
                        }
                        if (answerText.startsWith("\n")) {
                            continue;
                        }
                        boolean isCorrect = answerNode.getNodeName().equals("correctanswer");
                        answers.add(new Answer(answerText, isCorrect));
                    }

                    Question question = new Question(questionText, prize, answers);
                    container.addQuestionToContainer(prize, question);
                }
            }
        }

        return container;
    }
}
