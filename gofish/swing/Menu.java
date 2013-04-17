package gofish.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        
        JMenuItem newGame = new JMenuItem("New game");
        ActionListener newGameAction = new NewGameAction(game);
        newGame.addActionListener(newGameAction);
        
        JMenuItem restartGame = new JMenuItem("Restart game");
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        file.add(newGame);
        file.add(restartGame);
        file.addSeparator();
        file.add(exit);
        add(file);
        
        JMenu help = new JMenu("Help");
        
        JMenuItem about = new JMenuItem("About");
        ActionListener aboutAction = new AboutAction(game);
        about.addActionListener(aboutAction);
        
        help.add(about);
        add(help);
    }

}
