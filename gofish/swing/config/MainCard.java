package gofish.swing.config;

import gofish.swing.ConfigDialog;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainCard extends JPanel {
    
    private ConfigDialog dialog;
    
    public MainCard(ConfigDialog dialog) {
        super(new GridBagLayout());
        this.dialog = dialog;
        addButton(ConfigDialog.MANUAL, "Manual Configuration");
        addButton(ConfigDialog.XML, "XML Configuration");
    }
    
    private void addButton(final String card, String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.showCard(card);
            }
        });
        add(button);
    }

}
