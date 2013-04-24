package gofish.swing;

import gofish.swing.actions.NewGameAction;
import gofish.swing.actions.AboutAction;
import gofish.swing.actions.RestartGameAction;
import gofish.swing.actions.StopGameAction;
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
        add(buildFileMenu());
        add(buildHelpMenu());
    }
    
    private JMenu buildFileMenu() {
        JMenu menu = new JMenu("File");
        
        Action newGame = new NewGameAction(game);
        menu.add(newGame);
        
        Action stopGame = new StopGameAction(game);
        menu.add(stopGame);
        
        Action restartGame = new RestartGameAction(game);
        menu.add(restartGame);
        
        menu.addSeparator();
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.dispose();
                System.exit(0);
            }
        });        
        menu.add(exit);
        
        return menu;
    }

    private JMenu buildHelpMenu() {
        JMenu menu = new JMenu("Help");
        
        Action about = new AboutAction(game);        
        menu.add(about);
        
        return menu;
    }

}
