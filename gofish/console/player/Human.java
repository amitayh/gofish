package gofish.console.player;

import gofish.Game;
import gofish.console.ConsoleUtils;
import gofish.exception.NoCardsLeftException;
import gofish.model.Card;
import gofish.model.Player;
import java.util.List;
import java.util.Scanner;

public class Human extends Player {
    
    private Scanner input;

    public Human(Scanner input, String name) {
        super(Type.HUMAN, name);
        this.input = input;
    }

    @Override
    public Query getQuery(Game game) throws NoCardsLeftException {
        List<Player> otherPlayers = otherPlayers(game.getPlayers());
        Player playerAsked = getPlayerAsked(otherPlayers);
        String cardName = getCardName(playerAsked);
        return new Query(playerAsked, cardName);
    }
    
    private Player getPlayerAsked(List<Player> players) {
        System.out.println("Who do you want to ask a card from?");
        printPlayers(players);
        
        // Get input from user
        System.out.print("Enter player's number: ");
        int index = ConsoleUtils.nextInt(input, 1, players.size());
        return players.get(index - 1);
    }

    private String getCardName(Player playerAsked) throws NoCardsLeftException {
        printHand();
        System.out.println("Which card do you want from " + playerAsked.getName() + "?");
        System.out.print("Enter card's name (press Enter to quit game): ");
        String cardName = input.nextLine();
        if (cardName.isEmpty()) {
            throw new NoCardsLeftException();
        }
        return cardName;
        
    }
    
    private void printPlayers(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            System.out.println((i + 1) + ". " + player.getName());
        }
    }
    
    private void printHand() {
        System.out.println("\nYour hand:");
        for (Card card : getHand()) {
            System.out.println("* " + card.getName());
        }
    }
    
}
