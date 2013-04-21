package gofish.swing;

import gofish.model.Card;
import javax.swing.Icon;
import javax.swing.JLabel;

public class CardLabel extends JLabel {
    
    final private static int CARD_WIDTH = 75;
    
    final private static int CARD_HEIGHT = 105;
    
    final private static String IMAGES_PATH = "cards/";
    
    final private static String IMAGE_EXTENSION = ".png";
    
    final private static Icon BACK = getIcon("back");
    
    private Card card;
    
    private Icon icon;
    
    private boolean revealed = false;

    public CardLabel(Card card) {
        this.card = card;
        icon = getIcon(card.getName());
        init();
    }
    
    public void setRevealed(boolean flag) {
        if (flag) {
            if (icon != null) {
                setIcon(icon);
            } else {
                setText(card.getName());
            }
        } else {
            setIcon(BACK);
            setText("");
        }
        revealed = flag;
    }
    
    public boolean getRevealed() {
        return revealed;
    }
    
    private void init() {
        setSize(CARD_WIDTH, CARD_HEIGHT);
        setRevealed(false);
    }
    
    private static Icon getIcon(String name) {
        return SwingUtils.getIcon(
            // Convert "A of Spades" to "cards/a_of_spades.png"
            IMAGES_PATH + name.replace(' ', '_').toLowerCase() + IMAGE_EXTENSION
        );
    }

}
