package gofish.swing.player;

import gofish.exception.GameStoppedException;
import gofish.model.Card;
import gofish.model.CardsCollection;
import gofish.model.Player;
import gofish.model.Series;
import gofish.swing.CardLabel;
import gofish.swing.SwingUtils;
import java.awt.Color;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import javax.swing.Icon;
import javax.swing.JComponent;
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
    
    private JLabel nameLabel;
    
    private JLabel remainingCardsLabel;
    
    private JLabel remainingCardsCount;
    
    private JLabel completedSeriesLabel;
    
    private JLabel completedSereisCount;
    
    private CardsPanel handPanel;
    
    private JPanel completePanel;
    
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
        JComponent[] components = {
            imageLabel,
            nameLabel,
            remainingCardsLabel,
            remainingCardsCount,
            completedSeriesLabel,
            completedSereisCount
        };
        for (JComponent component : components) {
            component.setEnabled(false);
        }
    }
    
    public void showCompletedSeries(boolean flag) {
        showCompletedSeries = flag;
    }

    private void initComponents() {
        setLayout(new MigLayout("", "[48px][][grow]", "[][][][160px][160px]"));

        imageLabel = new JLabel(USER_ICON);
        add(imageLabel, "cell 0 0 1 3");
        
        nameLabel = new JLabel(player.getName());
        SwingUtils.makeBold(nameLabel);
        add(nameLabel, "cell 1 0 2 1");

        remainingCardsLabel = new JLabel("Remaining cards:");
        add(remainingCardsLabel, "cell 1 1");

        remainingCardsCount = new JLabel("0");
        add(remainingCardsCount, "cell 2 1");

        completedSeriesLabel = new JLabel("Completed series:");
        add(completedSeriesLabel, "cell 1 2");

        completedSereisCount = new JLabel("0");
        add(completedSereisCount, "cell 2 2");

        JPanel handContainer = new JPanel();
        handPanel = new CardsPanel();
        handContainer.add(handPanel);
        JScrollPane handScrollPane = createScrollPane("Cards in hand", handContainer);
        add(handScrollPane, "cell 0 3 3 1,grow");

        completePanel = new JPanel();
        JScrollPane completeScrollPane = createScrollPane("Completed series", completePanel);
        add(completeScrollPane, "cell 0 4 3 1,grow");
    }
    
    private JScrollPane createScrollPane(String title, JComponent viewport) {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        JLabel titleLabel = new JLabel(title);
        scrollPane.setColumnHeaderView(titleLabel);
        scrollPane.getColumnHeader().setBackground(Color.WHITE);
        
        scrollPane.setViewportView(viewport);
        
        return scrollPane;
    }

}
