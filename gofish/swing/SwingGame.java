package gofish.swing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingGame extends JFrame {
    
    final private static int WINDOW_WIDTH = 800;
    
    final private static int WINDOW_HEIGHT = 600;

    public SwingGame() {
        super();
        init();        
    }

    private void init() {
        setTitle("GoFish");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Container
        JPanel panel = new JPanel();
        
        JButton manual = new JButton("Manual configuration");
        JButton xml = new JButton("XML configuration");
        
        panel.add(manual);
        panel.add(xml);
        
        add(panel);
    }

}
