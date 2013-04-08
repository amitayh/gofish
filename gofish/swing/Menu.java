package gofish.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar implements ActionListener {
    
    private JMenuItem newGame;
    
    private JMenuItem restartGame;
    
    private JMenuItem exit;
    
    private JMenuItem about;
    
    public Menu() {
        buildMenu();
    }

    private void buildMenu() {
        JMenu game = new JMenu("Game");
        
        newGame = new JMenuItem("New game");
        newGame.addActionListener(this);
        
        restartGame = new JMenuItem("Restart game");
        restartGame.addActionListener(this);
        
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        
        game.add(newGame);
        game.add(restartGame);
        game.addSeparator();
        game.add(exit);
        add(game);
        
        JMenu help = new JMenu("Help");
        
        about = new JMenuItem("About");
        about.addActionListener(this);
        
        help.add(about);
        add(help);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == exit) {
            System.exit(0);
        } else if (source == about) {
            AboutDialog aboutDialog = new AboutDialog();
            aboutDialog.setVisible(true);
        }
    }

}
