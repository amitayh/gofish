package gofish.swing;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar {
    
    public Menu() {
        buildMenu();
    }

    private void buildMenu() {
        JMenu game = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem restartGame = new JMenuItem("Restart game");
        JMenuItem exit = new JMenuItem("Exit");
        
        game.add(newGame);
        game.add(restartGame);
        game.addSeparator();
        game.add(exit);
        
        add(game);
        
        JMenu help = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        
        help.add(about);
        add(help);
    }

}
