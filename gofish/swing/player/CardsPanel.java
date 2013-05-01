package gofish.swing.player;

import gofish.swing.CardLabel;
import java.awt.Dimension;
import javax.swing.JLayeredPane;

public class CardsPanel extends JLayeredPane {
    
    final public static int SPACING = 18;
    
    public void add(CardLabel card) {
        int numItems = getComponentCount();
        
        // Add cards with appropriate z-index
        super.add(card, new Integer(numItems));
        card.setLocation(SPACING * numItems, 0);
        
        // Update preferred size
        int width = numItems * SPACING + CardLabel.CARD_WIDTH;
        setPreferredSize(new Dimension(width, CardLabel.CARD_HEIGHT));
    }

}
