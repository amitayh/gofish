package gofish.swing.player;

import gofish.swing.CardLabel;
import javax.swing.JLayeredPane;

public class CardsPanel extends JLayeredPane {
    
    final public static int SPACING = 20;
    
    public void add(CardLabel card) {
        int numItems = getComponentCount();
        super.add(card, new Integer(numItems));
        card.setLocation(SPACING * numItems, 0);
    }

}
