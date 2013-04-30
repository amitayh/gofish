package gofish.swing.player;

import gofish.exception.GameStoppedException;
import gofish.model.Card;
import gofish.model.CardsCollection;
import gofish.model.Player;
import gofish.model.Series;
import gofish.swing.CardLabel;
import gofish.swing.SwingUtils;
import java.awt.Color;
import java.awt.Container;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.RoundedBalloonStyle;
import net.miginfocom.swing.MigLayout;

public class PlayerPanel extends JPanel {
    
    final private static Icon USER_ICON = SwingUtils.getIcon("user.png");
    
    final private static Color PLAYING_BACKGROUND = new Color(0xFFFEDF);
    
    final private static long BALLOON_TIME = TimeUnit.SECONDS.toMillis(2);
    
    final private static BalloonTipStyle BALLOON_STYLE =
        new RoundedBalloonStyle(5 ,5, Color.WHITE, Color.BLACK);
    
    private Player player;
    
    private boolean showCompletedSeries = false;
    
    private JLabel imageLabel;
    
    private JLabel remainingCardsCount;
    
    private JLabel completedSereisCount;
    
    private Container handPanel;
    
    private Container completePanel;
    
    public PlayerPanel(Player player) {
        this.player = player;
        initComponents();
    }
    
    public void isPlaying() {
        setBackground(PLAYING_BACKGROUND);
    }
    
    public void isNotPlaying() {
        setBackground(null);
    }
    
    public void say(String message) {
        BalloonTip tip = new BalloonTip(imageLabel, message, BALLOON_STYLE, false);
        try {
            Thread.sleep(BALLOON_TIME);
        } catch (InterruptedException e) {
            throw new GameStoppedException(e);
        } finally {
            tip.closeBalloon();
        }
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public final void updatePanels() {
        updateCompletePanel();
        updateHandPanel();
    }
    
    public void updateCompletePanel() {
        completePanel.removeAll();
        Collection<Series> completeSeries = player.getCompleteSeries();
        for (Series series : completeSeries) {
            SeriesPanel seriesPanel = new SeriesPanel(series, showCompletedSeries);
            completePanel.add(seriesPanel);
        }
        completePanel.repaint();
        
        Integer series = new Integer(completeSeries.size());
        completedSereisCount.setText(series.toString());
    }
    
    public void updateHandPanel() {
        handPanel.removeAll();
        CardsCollection hand = player.getHand();
        for (Card card : hand) {
            CardLabel cardLabel = new CardLabel(card);
            if (player.isHuman()) {
                cardLabel.setRevealed(true);
            }
            handPanel.add(cardLabel);
        }
        handPanel.repaint();
        
        Integer remainingCards = new Integer(hand.size());
        remainingCardsCount.setText(remainingCards.toString());
    }
    
    public void playerOut() {
        setEnabled(false);
    }
    
    public void showCompletedSeries(boolean flag) {
        showCompletedSeries = flag;
    }

    private void initComponents() {
        setLayout(new MigLayout("", "[48px][][grow]", "[][][][150px][150px]"));

        imageLabel = new JLabel(USER_ICON);
        add(imageLabel, "cell 0 0 1 3");
        
        String name = SwingUtils.bold(player.getName());
        JLabel nameLabel = new JLabel(name);
        add(nameLabel, "cell 1 0 2 1");

        JLabel remainingCardsLabel = new JLabel("Remaining cards:");
        add(remainingCardsLabel, "cell 1 1");

        remainingCardsCount = new JLabel("0");
        add(remainingCardsCount, "cell 2 1");

        JLabel completedSeriesLabel = new JLabel("Completed series:");
        add(completedSeriesLabel, "cell 1 2");

        completedSereisCount = new JLabel("0");
        add(completedSereisCount, "cell 2 2");

        JScrollPane handScrollPane = new JScrollPane();
        handScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(handScrollPane, "cell 0 3 3 1,grow");

        JLabel handTitle = new JLabel("Cards in hand");
        handScrollPane.setColumnHeaderView(handTitle);

        handPanel = new CardsPanel();
        handScrollPane.setViewportView(handPanel);

        JScrollPane completeScrollPane = new JScrollPane();
        completeScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(completeScrollPane, "cell 0 4 3 1,grow");

        JLabel completeTitle = new JLabel("Completed series");
        completeScrollPane.setColumnHeaderView(completeTitle);

        completePanel = new JPanel();
        completeScrollPane.setViewportView(completePanel);
    }

}
