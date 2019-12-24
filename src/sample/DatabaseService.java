package sample;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sample.Models.Answer;
import sample.Models.PriceCategory;
import sample.Models.Question;
import sample.Models.QuestionContainer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DatabaseService {

    private static final String XML_FILENAME = "src/sample/database.xml";

    private final XMLParserUtil parser;

    DatabaseService() throws IOException, SAXException, ParserConfigurationException {
        parser = new XMLParserUtil(new File(XML_FILENAME));
    }

    void fetchFromDatabaseIntoQuestionsContainer() {
        QuestionContainer containerInstance = QuestionContainer.getInstanceOfQuestionContainer();
        Document document = parser.getXmlDocument();

        NodeList difficultyNodes = document.getElementsByTagName("difficulty");
        for (int i = 0; i < difficultyNodes.getLength(); i++) {
            Node node = difficultyNodes.item(i);
            String priceString = node.getAttributes().getNamedItem("price").getNodeValue();
            PriceCategory price = PriceCategory.valueOf("LVL_" + priceString);

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

                    Question question = new Question(questionText, price, answers);
                    containerInstance.addQuestionToContainer(price, question);
                }
            }
        }
    }
}
