package gofish.config;

import gofish.Config;
import gofish.ConfigFactory;
import gofish.exception.ConfigValidationException;
import gofish.model.Card;
import gofish.model.Player;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

abstract public class XMLConfigFactory implements ConfigFactory {
    
    final private static String SCHEMA_FILE = "gofish/resources/gofish.xsd";
    
    final private static String TYPE_COMPUTER = "COMPUTER";
    
    final private static String TYPE_HUMAN = "HUMAN";
    
    private File file;
    
    private Element root;
    
    private Config config;
    
    @Override
    public Config getConfig() {
        return config;
    }
    
    public void validate(File file) throws ConfigValidationException {
        this.file = file;
        validateFile();
        setRootElement();
        createConfig();
        config.validate();
    }    
    
    private void validateFile() {
        if (!file.exists() || !file.isFile()) {
            throw new ConfigValidationException("File does not exist");
        }
    }
    
    private void setRootElement() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        factory.setNamespaceAware(true);
        
        DocumentBuilder builder;
        try {
            // XSD schema validation
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL url = this.getClass().getClassLoader().getResource(SCHEMA_FILE);
            factory.setSchema(schemaFactory.newSchema(url));
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new ExceptionErrorHandler());
        } catch (SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        
        try {
            Document doc = builder.parse(file);
            root = doc.getDocumentElement();
        } catch (SAXException e) {
            String message = "Schema validation failed - " + e.getMessage();
            throw new ConfigValidationException(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void createConfig() {
        config = new Config();
        
        Node settings = root.getElementsByTagName("settings").item(0);
        for (Node node = settings.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (node.getNodeName()) {
                    case "allowMutipleRequests":
                        config.setAllowMutipleRequests(getBooleanValue(node));
                        break;
                    case "forceShowOfSeries":
                        config.setForceShowOfSeries(getBooleanValue(node));
                        break;
                }
            }
        }
        
        Node players = root.getElementsByTagName("players").item(0);
        for (Node node = players.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("player")) {
                Player player = getPlayer((Element) node);
                config.addPlayer(player);
            }
        }
    }
    
    private Player getPlayer(Element playerElement) {
        Player player = null;
        String name = playerElement.getAttribute("name");
        String type = playerElement.getAttribute("type");
        switch (type) {
            case TYPE_COMPUTER:
                player = createPlayer(Player.Type.COMPUTER, name);
                break;
            case TYPE_HUMAN:
                player = createPlayer(Player.Type.HUMAN, name);
                break;
            default:
                throw new ConfigValidationException("Unexpected player type - " + type);
        }
        
        if (player != null) {
            Node cards = playerElement.getElementsByTagName("cards").item(0);
            for (Node node = cards.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("card")) {
                    Card card = getCard((Element) node);
                    player.addCard(card);
                }
            }
        }
        
        return player;
    }
    
    private Card getCard(Element cardElement) {
        String name = cardElement.getAttribute("name");
        List<String> properties = new ArrayList<>();
        for (Node node = cardElement.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("series")) {
                properties.add(node.getTextContent());
            }
        }
        
        return new Card(name, properties);
    }
    
    private boolean getBooleanValue(Node node) {
        return node.getTextContent().equals("true");
    }
    
    abstract protected Player createPlayer(Player.Type type, String name);
    
    private static class ExceptionErrorHandler implements ErrorHandler {

        @Override
        public void warning(SAXParseException e) throws SAXException {
            throw e;
        }

        @Override
        public void error(SAXParseException e) throws SAXException {
            throw e;
        }

        @Override
        public void fatalError(SAXParseException e) throws SAXException {
            throw e;
        }
        
    }

}
