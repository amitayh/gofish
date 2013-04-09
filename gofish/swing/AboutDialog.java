package gofish.swing;

import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
    
    public AboutDialog(JFrame parent) {
        super(parent, "GoFish - About", true);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(new JLabel("GoFish v2.0 - Swing UI"));
        contentPane.add(new JLabel("Created by Amitay Horwitz"));
        
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}
