package gofish.swing;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class AboutDialog extends JDialog {
    
    public AboutDialog(JFrame parent) {
        super(parent, "GoFish - About", true);
        
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}
