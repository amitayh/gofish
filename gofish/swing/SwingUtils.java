package gofish.swing;

import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
    
    public static String bold(String text) {
        return "<html><b>" + text + "</b></html>";
    }
    
    public static String error(String text) {
        return "<html><b color='red'>" + text + "</b></html>";
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
