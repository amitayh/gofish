package gofish.swing;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class SwingGame extends JFrame {

    public SwingGame() {
        init();        
    }

    private void init() {
        setTitle("GoFish");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setJMenuBar(new Menu());
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBorder(new EmptyBorder(5, 5, 5, 5));
        tabs.addTab("Config", new Config());
        tabs.addTab("Game", new JPanel());
        add(tabs);
        
        pack();
        setLocationRelativeTo(null);
    }

}
