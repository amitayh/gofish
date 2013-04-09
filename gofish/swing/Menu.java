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
    
    private SwingGame game;
    
    public Menu(SwingGame game) {
        this.game = game;
        buildMenu();
    }

    private void buildMenu() {
        JMenu file = new JMenu("File");
        
        newGame = new JMenuItem("New game");
        newGame.addActionListener(this);
        
        restartGame = new JMenuItem("Restart game");
        restartGame.addActionListener(this);
        
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        
        file.add(newGame);
        file.add(restartGame);
        file.addSeparator();
        file.add(exit);
        add(file);
        
        JMenu help = new JMenu("Help");
        
        about = new JMenuItem("About");
        about.addActionListener(this);
        
        help.add(about);
        add(help);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == newGame) {
            game.configure();
        } else if (source == exit) {
            System.exit(0);
        } else if (source == about) {
            game.about();
        }
    }

}
