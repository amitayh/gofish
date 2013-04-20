package gofish.swing;

import javax.swing.Icon;
import javax.swing.JLabel;

public class CompleteLabel extends JLabel {
    
    final private static String ICON_PATH = "cards/series.png";
    
    final private static Icon ICON = SwingUtils.getIcon(ICON_PATH);
    
    public CompleteLabel() {
        setSize(52, 68);
        setIcon(ICON);
    }

}
