package gofish.swing;

import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

public class AboutDialog extends JDialog {
    
    public AboutDialog(JFrame parent) {
        super(parent, "GoFish - About", true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout("", "[grow]", "[grow][][][][grow]"));
        JLabel label;
        
        label = new JLabel(SwingUtils.bold("GoFish v2.0 - Swing UI"));
        contentPane.add(label, "cell 0 1,alignx center");
        
        label = new JLabel("Created by Amitay Horwitz");
        contentPane.add(label, "cell 0 2,alignx center");
        
        label = new JLabel("Internet & Java - Spring 2013");
        contentPane.add(label, "cell 0 3,alignx center");
        
        setSize(250, 120);
        setResizable(false);
    }
    
    @Override
    public void setVisible(boolean flag) {
        if (flag) {
            setLocationRelativeTo(getParent());
        }
        super.setVisible(flag);
    }

}
