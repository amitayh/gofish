package gofish.console.config;

import gofish.Config;
import gofish.ConfigFactory;
import gofish.Game;
import gofish.console.ConsoleUtils;
import gofish.console.player.Computer;
import gofish.console.player.Human;
import gofish.model.Deck;
import gofish.model.Player;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ManualConfig implements ConfigFactory {
    
    final private static int OPTION_COMPUTER = 1;
    
    final private static int OPTION_HUMAN = 2;
    
    private Scanner input;

    public ManualConfig(Scanner input) {
        this.input = input;
    }
    
    @Override
    public Config getConfig() {
        Config config = new Config();
        
        System.out.println("Configure game");
        
        System.out.print("Allow multiple requests? (y/n): ");
        config.setAllowMutipleRequests(ConsoleUtils.nextBoolean(input));
        
        System.out.print("Force show completed series? (y/n): ");
        config.setForceShowOfSeries(ConsoleUtils.nextBoolean(input));
        
        System.out.print("Enter number of players: ");
        int min = Game.MIN_NUM_PLAYERS, max = Game.MAX_NUM_PLAYERS;
        int numPlayers = ConsoleUtils.nextInt(input, min, max);
        
        // Create players
        Player[] players = new Player[numPlayers];
        Set<String> playerNames = new HashSet<>();
        for (int i = 0; i < numPlayers; i++) {
            System.out.println("\nConfigure player #" + (i + 1));
            Player player = getPlayer(playerNames);
            config.addPlayer(player);
            players[i] = player;
        }
        
        // Deal a stanrad 52 cards deck
        Deck deck = new Deck();
        deck.shuffle();
        int playerIndex = 0;
        while (deck.size() > 0) {
            players[playerIndex].addCard(deck.deal());
            playerIndex = (playerIndex + 1) % numPlayers;
        }
        
        return config;
    }
    
    private Player getPlayer(Set<String> playerNames) {
        Player player = null;
        
        System.out.print("Enter player's name: ");
        String name = input.nextLine();
        while (playerNames.contains(name)) {
            System.out.print("'" + name + "' is already being used. Choose another name: ");
            name = input.nextLine();
        }
        playerNames.add(name);
        
        System.out.println("Choose player type");
        System.out.println("1. Computer");
        System.out.println("2. Human");
        System.out.print("Enter type: ");
        switch (ConsoleUtils.nextInt(input, 1, 2)) {
            case OPTION_COMPUTER:
                player = new Computer(name);
                break;
            case OPTION_HUMAN:
                player = new Human(input, name);
                break;
        }
        
        return player;
    }

}
