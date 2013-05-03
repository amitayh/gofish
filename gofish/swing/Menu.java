package gofish.swing;

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
        add(buildFileMenu());
        add(buildHelpMenu());
    }
    
    private JMenu buildFileMenu() {
        JMenu menu = new JMenu("File");
        
        menu.add(game.getNewGameAction());
        menu.add(game.getStopGameAction());
        menu.add(game.getRestartGameAction());
        
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
