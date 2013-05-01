package gofish.swing.player;

import gofish.model.Card;
import gofish.model.Series;
import gofish.swing.CardLabel;

public class SeriesPanel extends CardsPanel {

    public SeriesPanel(Series series, boolean isRevealed) {
        for (Card card : series.getCards()) {
            CardLabel cardLabel = new CardLabel(card);
            cardLabel.setRevealed(isRevealed);
            add(cardLabel);
        }
    }

}
