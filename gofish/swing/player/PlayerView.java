package gofish.swing.player;

import gofish.model.Player;
import gofish.swing.SwingUtils;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.BalloonTipStyle;
import net.java.balloontip.styles.RoundedBalloonStyle;

public class PlayerView extends JPanel {
    
    final private static Color PLAYING_BACKGROUND = new Color(0xFFFEDF);
    
    final private static BalloonTipStyle BALLOON_STYLE =
        new RoundedBalloonStyle(5 ,5, Color.WHITE, Color.BLACK);
    
    private JLabel name;
    
    public PlayerView(Player player) {
        String labelText = SwingUtils.boldText(player.getName());
        name = new JLabel(labelText);
        add(name);
    }
    
    public void isPlaying() {
        setBackground(PLAYING_BACKGROUND);
    }
    
    public void isNotPlaying() {
        setBackground(null);
    }
    
    public void say(String message) {
        BalloonTip tip = new BalloonTip(name, message, BALLOON_STYLE, false);
    }

}
