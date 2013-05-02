package gofish.swing.player;

import gofish.model.Card;
import gofish.swing.CardLabel;
import java.awt.Dimension;
import java.util.Collection;
import javax.swing.JLayeredPane;

public class CardsPanel extends JLayeredPane {
    
    final public static int SPACING = 18; // Spacing between overlapping cards
    
    public void setCards(Collection<Card> cards, boolean revealed) {
        removeAll();
        int index = 0;
        for (Card card : cards) {
            CardLabel cardLabel = new CardLabel(card, revealed);
            addCard(cardLabel, index);
            index++;
        }
        updatePreferredSize(index);
    }
    
    private void addCard(CardLabel cardLabel, int index) {
        // Add card with appropriate z-index
        add(cardLabel, new Integer(index));
        cardLabel.setLocation(SPACING * index, 0);
    }
    
    private void updatePreferredSize(int numCards) {
        int width = ((numCards - 1) * SPACING) + CardLabel.CARD_WIDTH;
        setPreferredSize(new Dimension(width, CardLabel.CARD_HEIGHT));
    }

}
