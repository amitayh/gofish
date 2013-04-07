package gofish.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class SwingGame extends JFrame {
    
    final private static int WINDOW_WIDTH = 800;
    
    final private static int WINDOW_HEIGHT = 600;

    public SwingGame() {
        init();        
    }

    private void init() {
        setTitle("GoFish");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setJMenuBar(new Menu());
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Config", new Config());
        tabs.addTab("Game", new JPanel());
        
        add(tabs);
    }

}
