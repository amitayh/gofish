package gofish.swing;

import gofish.swing.config.MainCard;
import gofish.swing.config.ManualCard;
import gofish.swing.config.XMLCard;
import java.awt.CardLayout;
import java.awt.Container;
import javax.swing.JDialog;

public class ConfigDialog extends JDialog {
    
    final public static String MAIN = "main";
    
    final public static String MANUAL = "manual";
    
    final public static String XML = "xml";
    
    private SwingGame game;

    public ConfigDialog(SwingGame game) {
        super(game, "GoFish - Game Configuration", true);
        this.game = game;
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new CardLayout());
        contentPane.add(new MainCard(this), MAIN);
        contentPane.add(new ManualCard(game, this), MANUAL);
        contentPane.add(new XMLCard(game, this), XML);
        
        pack();
        setResizable(false);
    }
    
    public void showCard(String name) {
        Container contentPane = getContentPane();
        CardLayout layout = (CardLayout) contentPane.getLayout();
        layout.show(contentPane, name);
    }

    @Override
    public void setVisible(boolean b) {
        setLocationRelativeTo(game);
        showCard(MAIN);
        super.setVisible(b);
    }
    
    

}
