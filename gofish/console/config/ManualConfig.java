package gofish.console.config;

import gofish.config.Config;
import gofish.config.ConfigFactory;
import gofish.Game;
import gofish.console.ConsoleUtils;
import gofish.model.player.Computer;
import gofish.console.player.Human;
import gofish.exception.PlayerCollisionException;
import gofish.model.Card;
import gofish.model.Deck;
import gofish.model.Player;
import java.util.List;
import java.util.Scanner;

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
        for (int i = 0; i < numPlayers; i++) {
            boolean playerAdded = false;
            Player player;
            do {
                System.out.println("\nConfigure player #" + (i + 1));
                player = getPlayer();
                try {
                    config.addPlayer(player);
                    playerAdded = true;
                } catch (PlayerCollisionException e) {
                    System.out.println("Name '" + e.getName() +
                                       "' is already being used!");
                }
            } while (!playerAdded);
        }
        
        // Deal a stanrad 52 cards deck
        dealCards(config.getPlayers());
        
        return config;
    }
    
    private Player getPlayer() {
        Player player = null;
        
        System.out.print("Enter player's name: ");
        String name = input.nextLine();
        
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
    
    private void dealCards(List<Player> players) {
        Deck deck = new Deck();
        deck.shuffle();
        
        int index = 0;
        while (deck.size() > 0) {
            Player player = players.get(index);
            Card card = deck.deal();
            player.addCard(card);
            index = (index + 1) % players.size();
        }
    }

}
