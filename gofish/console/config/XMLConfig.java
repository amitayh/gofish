package gofish.console.config;

import gofish.Config;
import gofish.ConfigFactory;
import gofish.config.XMLConfigFactory;
import gofish.console.player.Computer;
import gofish.console.player.Human;
import gofish.exception.ConfigValidationException;
import gofish.model.Player;
import java.io.File;
import java.util.Scanner;

public class XMLConfig implements ConfigFactory {
    
    private Scanner input;

    public XMLConfig(Scanner input) {
        this.input = input;
    }

    @Override
    public Config getConfig() {
        XMLConfigFactory factory = new Factory();
        boolean isValid = false;
        Config config = null;
        
        do {            
            try {
                factory.validate(getFile());
                config = factory.getConfig();
                isValid = true;
            } catch (ConfigValidationException e) {
                System.out.println("Invalid config file - " + e.getMessage());
            }
        } while (!isValid);
        
        return config;
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
    
    private class Factory extends XMLConfigFactory {

        @Override
        protected Player createPlayer(Player.Type type, String name) {
            Player player;
            switch (type) {
                case COMPUTER: 
                    player = new Computer(name);
                    break;
                case HUMAN:
                    player = new Human(input, name);
                    break;
                default:
                    throw new RuntimeException("Unexpected player type");
            }
            return player;
        }
        
    }

}
