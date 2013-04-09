package gofish.swing.config;

import gofish.swing.ConfigDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

abstract public class ConfigCard extends JPanel {
    
    protected JPanel center;
    
    protected JPanel bottom;
    
    protected ActionListener listener;
    
    public ConfigCard(ActionListener listener) {
        super(new BorderLayout());
        this.listener = listener;
        initUI();        
    }

    private void initUI() {
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        center = new JPanel(new GridBagLayout());
        add(center, BorderLayout.CENTER);
        
        LayoutManager layout = new FlowLayout(FlowLayout.RIGHT);
        bottom = new JPanel(layout);
        add(bottom, BorderLayout.PAGE_END);
        
        JButton back = new JButton("Back");
        back.setName(ConfigDialog.MAIN);
        back.addActionListener(listener);
        bottom.add(back);
        
        JButton start = new JButton("Start game");
        bottom.add(start);
        
        initComponents();
    }
    
    abstract protected void initComponents();

}
