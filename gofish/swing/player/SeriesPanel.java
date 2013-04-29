package gofish.swing.player;

import gofish.Game;
import gofish.model.Card;
import gofish.model.Series;
import gofish.swing.CardLabel;
import java.awt.Dimension;

public class SeriesPanel extends CardsPanel {
    
    final private static int DEFAULT_WIDTH;
    
    final private static int DEFAULT_HEIGHT = CardLabel.CARD_HEIGHT;
    
    static {
        int cardWidth = CardLabel.CARD_WIDTH;
        int seriesSize = Game.COMPLETE_SERIES_SIZE;
        int spacing = CardsPanel.SPACING * (seriesSize - 1);
        DEFAULT_WIDTH = cardWidth + spacing;
    }

    public SeriesPanel(Series series, boolean isRevealed) {
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        for (Card card : series.getCards()) {
            CardLabel cardLabel = new CardLabel(card);
            cardLabel.setRevealed(isRevealed);
            add(cardLabel);
        }
    }

}
