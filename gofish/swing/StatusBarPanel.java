package gofish.swing;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class StatusBarPanel extends JPanel {
    
    private JLabel label;

    public StatusBarPanel(String text) {
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        label = new JLabel(text);
        add(label);
    }
    
    public void setText(String text) {
        label.setText(text);
    }

}
