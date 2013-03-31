package gofish.console.config;

import gofish.Config;
import gofish.ConfigFactory;
import gofish.console.player.Computer;
import gofish.console.player.Human;
import gofish.exception.ConfigValidationException;
import gofish.model.Card;
import gofish.model.Player;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLConfig implements ConfigFactory {
    
    final private static String SCHEMA_FILE = "gofish/resources/gofish.xsd";
    
    final private static String TYPE_COMPUTER = "COMPUTER";
    
    final private static String TYPE_HUMAN = "HUMAN";
    
    private Scanner input;

    public XMLConfig(Scanner input) {
        this.input = input;
    }

    @Override
    public Config getConfig() {
        boolean isValid = false;
        Config config = null;
        
        do {            
            try {
                config = createConfig();
                config.validate();
                isValid = true;
            } catch (ConfigValidationException e) {
                System.out.println("Invalid config file - " + e.getMessage());
            }
        } while (!isValid);
        
        return config;
    }
    
    private Config createConfig() {
        Config config = new Config();
        
        Element root = getXml();
        
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
        
        return config;
    }
    
    private Element getXml() {
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
            builder.setErrorHandler(new DefaultHandler()); // Suppress warnings
        } catch (SAXException | ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        
        boolean isValid = false;
        Element root = null;
        do {
            try {
                Document doc = builder.parse(getFile());
                root = doc.getDocumentElement();
                isValid = true;
            } catch (SAXException e) {
                System.out.println("Schema validation failed - " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (!isValid);
        
        return root;
    }
    
    private File getFile() {
        System.out.print("Enter file name: ");
        File file = new File(input.nextLine());
        while (!file.exists() || !file.isFile()) {
            System.out.print("File does not exist, try again: ");
            file = new File(input.nextLine());
        }
        return file;
    }
    
    private Player getPlayer(Element playerElement) {
        Player player = null;
        String name = playerElement.getAttribute("name");
        String type = playerElement.getAttribute("type");
        switch (type) {
            case TYPE_COMPUTER:
                player = new Computer(name);
                break;
            case TYPE_HUMAN:
                player = new Human(input, name);
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

}
