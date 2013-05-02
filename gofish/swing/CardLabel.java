package gofish.swing;

import gofish.model.Card;
import javax.swing.Icon;
import javax.swing.JLabel;

public class CardLabel extends JLabel {
    
    final public static int CARD_WIDTH = 78;
    
    final public static int CARD_HEIGHT = 105;
    
    final private static String IMAGES_PATH = "cards/";
    
    final private static String IMAGE_EXTENSION = ".png";
    
    final private static Icon BACK = getIcon("back");
    
    final private static Icon EMPTY = getIcon("empty");
    
    private Card card;
    
    private Icon icon;

    public CardLabel(Card card, boolean revealed) {
        this.card = card;
        setVerticalTextPosition(JLabel.CENTER);
        setHorizontalTextPosition(JLabel.CENTER);
        setSize(CARD_WIDTH, CARD_HEIGHT);
        icon = getIcon(card.getName());
        setRevealed(revealed);
    }
    
    final public void setRevealed(boolean flag) {
        if (flag) {
            // Reveal card
            if (icon != null) {
                // Card icon available - display it
                setIcon(icon);
            } else {
                // No icon available - display card's name
                setIcon(EMPTY);
                setText(card.getName());
            }
        } else {
            // Conceal card
            setIcon(BACK);
            setText("");
        }
    }
    
    private static Icon getIcon(String name) {
        return SwingUtils.getIcon(
            // Convert "A of Spades" to "cards/a_of_spades.png"
            IMAGES_PATH + name.replace(' ', '_').toLowerCase() + IMAGE_EXTENSION
        );
    }

}
