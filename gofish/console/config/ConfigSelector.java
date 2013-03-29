package gofish.console.config;

import gofish.Config;
import gofish.ConfigFactory;
import gofish.console.ConsoleUtils;
import java.util.Scanner;

public class ConfigSelector implements ConfigFactory {
    
    final private static int OPTION_MANUAL = 1;
    
    final private static int OPTION_XML = 2;
    
    private Scanner input = new Scanner(System.in);
    
    @Override
    public Config getConfig() {
        System.out.println("Choose configuration method");
        System.out.println("1. Manual");
        System.out.println("2. XML");
        System.out.print("Enter method number: ");
        
        ConfigFactory factory;
        switch (ConsoleUtils.nextInt(input, 1, 2)) {
            case OPTION_MANUAL:
                factory = new ManualConfig(input);
                break;
            case OPTION_XML:
                factory = new XMLConfig(input);
                break;
            default:
                // This should never happen
                return null;
        }
        
        System.out.println();
        
        return factory.getConfig();
    }

}
