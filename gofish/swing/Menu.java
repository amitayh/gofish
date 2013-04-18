package gofish.swing;

import gofish.swing.actions.NewGameAction;
import gofish.swing.actions.AboutAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar {
    
    private SwingGame game;
    
    public Menu(SwingGame game) {
        this.game = game;
        buildMenu();
    }

    private void buildMenu() {
        JMenu file = new JMenu("File");
        
        Action newGameAction = new NewGameAction(game);
        JMenuItem newGame = new JMenuItem(newGameAction);
        
        JMenuItem restartGame = new JMenuItem("Restart game");
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.dispose();
                System.exit(0);
            }
        });
        
        file.add(newGame);
        file.add(restartGame);
        file.addSeparator();
        file.add(exit);
        add(file);
        
        JMenu help = new JMenu("Help");
        
        Action aboutAction = new AboutAction(game);
        JMenuItem about = new JMenuItem(aboutAction);
        
        help.add(about);
        add(help);
    }

}
