package gofish;

import gofish.model.Card;
import gofish.model.Deck;
import gofish.model.Player;
import gofish.swing.SwingGame;
import gofish.swing.SwingUtils;
import gofish.swing.player.AbstractPlayer;
import gofish.swing.player.Computer;
import gofish.swing.player.Human;
import javax.swing.SwingUtilities;

public class SwingMain {

    public static void main(String[] args) {
        SwingUtils.setSystemLookAndFeel();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SwingGame game = new SwingGame();
                game.setVisible(true);
            }
        });
    }
    
    private Config getConfig(SwingGame game) {
        Config config = new Config();
        
        config.setAllowMutipleRequests(true);
        config.setForceShowOfSeries(true);
        
        AbstractPlayer players[] = {
            new Human("Amitay"),
            new Computer("Alice"),
            new Computer("Bob"),
            new Computer("Eve")
        };
        
        dealCards(players);
        for (AbstractPlayer player : players) {
            config.addPlayer(player);
            player.setGame(game);
        }
        
        return config;
    }

    private void dealCards(AbstractPlayer[] players) {
        Deck deck = new Deck();
        deck.shuffle();
        
        int index = 0;
        while (deck.size() > 0) {
            Player player = players[index];
            Card card = deck.deal();
            player.addCard(card);
            index = (index + 1) % players.length;
        }
    }
    
}
