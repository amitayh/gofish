package gofish.swing.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class MainCard extends JPanel {
    
    private ConfigDialog dialog;
    
    public MainCard(ConfigDialog dialog) {
        this.dialog = dialog;
        
        setLayout(new MigLayout("", "[grow][][][grow]", "[grow][][grow]"));
        
        JButton manualButton = createButton(ConfigDialog.MANUAL, "Manual Configuration");
        add(manualButton, "cell 1 1");
        
        JButton xmlButton = createButton(ConfigDialog.XML, "XML Configuration");
        add(xmlButton, "cell 2 1");
    }
    
    private JButton createButton(final String card, String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.showCard(card);
            }
        });
        return button;
    }

}
