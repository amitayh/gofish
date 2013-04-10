package gofish.swing;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
    
    public AboutDialog(JFrame parent) {
        super(parent, "GoFish - About", true);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        JLabel label;
        
        constraints.gridy = 0;
        label = new JLabel(SwingUtils.bold("GoFish v2.0 - Swing UI"));
        contentPane.add(label, constraints);
        
        constraints.gridy = 1;
        label = new JLabel("Created by Amitay Horwitz");
        contentPane.add(label, constraints);
        
        constraints.gridy = 2;
        label = new JLabel("Internet & Java - Spring 2013");
        contentPane.add(label, constraints);
        
        setSize(250, 120);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

}
