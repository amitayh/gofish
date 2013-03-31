package gofish.console;

import gofish.model.Card;
import gofish.model.Player;
import gofish.GUIRenderer;
import gofish.model.Series;
import java.io.IOException;
import java.util.Collection;

public class ConsoleRenderer implements GUIRenderer {

    @Override
    public void startGame() {
        System.out.println("Game started!\n");
    }
    
    @Override
    public void endGame(Player winner) {
        int numCompleteSeries = winner.getCompleteSeries().size();
        System.out.println("Game ended! Winner is " + winner.getName() +
                           " with " + numCompleteSeries + " complete series");
    }

    @Override
    public void playerTurn(Player player) {
        System.out.print(player.getName() + " is playing");
        if (player.getType() == Player.Type.COMPUTER) {
            System.out.print(" (press Enter to continue)");
            try {
                // Wait for user input
                System.in.read();
            } catch (IOException e) {}
        } else {
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void showSeries(Player player) {
        Collection<Series> complete = player.getCompleteSeries();
        System.out.println(player.getName() + " has " + complete.size() +
                           " complete series");
        
        for (Series series : complete) {
            System.out.println("Series of '" + series.getProperty() + "':");
            for (Card card : series.getCards()) {
                System.out.println("* " + card.getName());
            }
        }
        System.out.println();
    }
    
    @Override
    public void invalidQuery(Player.Query query) {
        System.out.println("Invalid query!");
    }

    @Override
    public void playerQuery(Player.Query query) {
        System.out.println(
            query.getPlayerAsking().getName() + " asked " +
            query.getPlayerAsked().getName() + " for card '" +
            query.getCardName() + "'"
        );
    }

    @Override
    public void playerOut(Player player) {
        System.out.println(player.getName() + " is out of the game\n");
    }
    
    @Override
    public void moveCard(Player from, Player to, Card card) {
        System.out.println(
            from.getName() + " gives card '" + card.getName() + "' to " + to.getName() + "\n"
        );
    }

    @Override
    public void seriesCompleted(Player player, Series series) {
        System.out.println(player.getName() + " completed a series!");
    }

    @Override
    public void goFish(Player player) {
        System.out.println(player.getName() + " goes fishing\n");
    }

    @Override
    public void error(String message) {
        System.err.println("Error occurred - " + message);
    }

}
