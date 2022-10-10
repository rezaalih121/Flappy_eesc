import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class XmlDbFileHandler {

    private File inputFile = new File(getPath());
    private static DocumentBuilderFactory dbFactory;
    private static DocumentBuilder dBuilder;
    private static Document doc;
    private static TransformerFactory transformerFactory;

    private static Transformer transformer;

    private ResourceBundle bundle;

    public XmlDbFileHandler(ResourceBundle bundle) {
        bundle = bundle;
        dbFactory = DocumentBuilderFactory.newInstance();
        transformerFactory = TransformerFactory.newInstance();

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            transformer = transformerFactory.newTransformer();

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveNewDataToXmlFile() {
        // write the content on console

        try {
            DOMSource source = new DOMSource(doc);
            System.out.println("-----------Modified File-----------");

            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            StreamResult result = new StreamResult(new File(getPath()));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getPath() {
        return System.getProperty("user.home") + "\\IdeaProjects\\Flappy_eesc\\src\\main\\java\\userDatabase.xml";
    }

    protected Element getUserElementByUserId(String userId) {
        Element userElement = null;
        try {
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("userId");
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (nodeList.item(i).getTextContent().equals(userId) ) {
                    userElement = (Element) nodeList.item(i).getParentNode();

                    break;
                }

            }
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //System.out.println("Root element :" + userElement.getNodeName() + "="+userElement.getElementsByTagName("userId").item(0).getTextContent());
        return userElement;
    }

    protected User getUserFromXmlFile(String userId) {
        User user = null;
        //String userName = userId;

        UserHistory userHistory = null;

        String gameResult;
        String datePlayed;
        String gameLevel;


        try {

            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            Element userElement = getUserElementByUserId(userId);
            if (userElement != null && userElement.getElementsByTagName("userId").item(0).getTextContent().equals(userId) ) {

                user = new User(userId);

                NodeList nList = userElement.getElementsByTagName("userHistory");
                //System.out.println("----------------------------");
                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);


                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element hElement = (Element) nNode;
                        System.out.println("\n Current element : " + hElement.getElementsByTagName("gameResult")
                                .item(0)
                                .getTextContent());
                        gameResult = hElement.getElementsByTagName("gameResult")
                                .item(0)
                                .getTextContent();
                        datePlayed = hElement.getElementsByTagName("datePlayed")
                                .item(0)
                                .getTextContent();
                        gameLevel = hElement.getElementsByTagName("gameLevel")
                                .item(0)
                                .getTextContent();

                        userHistory = new UserHistory(gameResult, datePlayed, gameLevel);


                    }
                    user.addUserHistory(userHistory);
                }

            } else {
                user = new User(userId);
                saveUserToXmlFile(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user.getUserName()+"?????????????");
        return user;
    }

    private void saveUserToXmlFile(User user) {
        try {

            doc = dBuilder.parse(inputFile);

            Node happyBirdDb = doc.getFirstChild();
            System.out.println(happyBirdDb.getNodeName());

            Element userElement = doc.createElement("user");

            userElement.setAttribute("userId", user.getUserName());
            userElement.setIdAttribute("userId", true);

            Element userIdElement = doc.createElement("userId");
            userIdElement.appendChild(doc.createTextNode(user.getUserName()));
            userElement.appendChild(userIdElement);

            updateUserGameHistory(user);
            happyBirdDb.appendChild(userElement);
            saveNewDataToXmlFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void updateUserInToXmlFile(User user) {
        try {

            doc = dBuilder.parse(inputFile);
            Node happyBirdDb = doc.getFirstChild();

            deleteUserGameHistory(user);
            updateUserGameHistory(user);

            // write the content on console
            saveNewDataToXmlFile();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void deleteUserGameHistory(User user) {

        Element userElement = getUserElementByUserId(user.userName);


        NodeList userHistoryNodes = userElement.getElementsByTagName("userHistory");
        for (int count = 0; count < userHistoryNodes.getLength(); count++) {
            Node node = userHistoryNodes.item(count);

            if ("userHistory".equals(node.getNodeName()))
                userElement.removeChild(node);
        }
        saveNewDataToXmlFile();
    }

    protected void updateUserGameHistory(User user) {

        if (user.getUserHistoriesList().size() > 0) {
            Element userElement = getUserElementByUserId(user.userName);

            for (UserHistory userHistory : user.getUserHistoriesList()) {
                Element userHistoryElement = doc.createElement("userHistory");
                Element gameResultElement = doc.createElement("gameResult");
                gameResultElement.appendChild(doc.createTextNode(userHistory.getGameResult()));
                userHistoryElement.appendChild(gameResultElement);

                Element datePlayedElement = doc.createElement("datePlayed");
                datePlayedElement.appendChild(doc.createTextNode(userHistory.getDatePlayed()));
                userHistoryElement.appendChild(datePlayedElement);

                Element gameLevelElement = doc.createElement("gameLevel");
                gameLevelElement.appendChild(doc.createTextNode(userHistory.getGameLevel()));
                userHistoryElement.appendChild(gameLevelElement);
                userElement.appendChild(userHistoryElement);
            }

        }

    }
}
