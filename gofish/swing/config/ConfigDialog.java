package gofish.swing.config;

import gofish.swing.SwingGame;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class ConfigDialog extends JDialog {
    
    final private static int DEFAULT_WIDTH = 450;
    
    final private static int DEFAULT_HEIGHT = 450;
    
    final public static String MAIN = "main";
    
    final public static String MANUAL = "manual";
    
    final public static String XML = "xml";

    public ConfigDialog(SwingGame game) {
        super(game, "GoFish - Game Configuration", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new CardLayout());
        
        JPanel main = new MainCard(this);
        JPanel manual = new ManualCard(game, this);
        JPanel xml = new XMLCard(game, this);
        
        contentPane.add(main, MAIN);
        contentPane.add(manual, MANUAL);
        contentPane.add(xml, XML);
        
        setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
    }
    
    public void showCard(String name) {
        Container contentPane = getContentPane();
        CardLayout layout = (CardLayout) contentPane.getLayout();
        layout.show(contentPane, name);
    }

    @Override
    public void setVisible(boolean flag) {
        if (flag) {
            showCard(MAIN);
            setLocationRelativeTo(getParent());
        }
        super.setVisible(flag);
    }

}
