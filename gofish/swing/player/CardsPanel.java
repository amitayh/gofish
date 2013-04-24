package gofish.swing.player;

import gofish.swing.CardLabel;
import javax.swing.JLayeredPane;

public class CardsPanel extends JLayeredPane {
    
    final private static int PADDING = 10;
    
    final private static int VGAP = 20;
    
    public void add(CardLabel card) {
        int numItems = getComponentCount();
        super.add(card, new Integer(numItems));
        card.setLocation(PADDING + (VGAP * numItems), PADDING);
    }

}
