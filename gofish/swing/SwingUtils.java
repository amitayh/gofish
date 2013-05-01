package gofish.swing;

import java.awt.Font;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class SwingUtils {
    
    final private static String IMAGES_PATH = "gofish/resources/images/";
    
    public static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Fallback to default
        }
    }
    
    public static void makeBold(JLabel label) {
        Font font = label.getFont();
        label.setFont(font.deriveFont(Font.BOLD));
    }
    
    public static Icon getIcon(String name) {
        Icon icon = null;
        String fileName = IMAGES_PATH + name;
        URL url = CardLabel.class.getClassLoader().getResource(fileName);
        if (url != null) {
            icon = new ImageIcon(url);
        }
        return icon;
    }

}
