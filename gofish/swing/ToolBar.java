package gofish.swing;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {
    
    final public static Icon NEW_GAME_ICON = SwingUtils.getIcon("page_add.png");
    
    final public static Icon STOP_GAME_ICON = SwingUtils.getIcon("page_delete.png");
    
    final public static Icon RESTART_GAME_ICON = SwingUtils.getIcon("page_copy.png");
    
    private SwingGame game;
    
    public ToolBar(SwingGame game) {
        this.game = game;
        buildToolbar();
    }

    private void buildToolbar() {
        JButton newGame = new JButton(game.getNewGameAction());
        newGame.setIcon(NEW_GAME_ICON);
        add(newGame);
        
        JButton stopGame = new JButton(game.getStopGameAction());
        stopGame.setIcon(STOP_GAME_ICON);
        add(stopGame);
        
        JButton restartGame = new JButton(game.getRestartGameAction());
        restartGame.setIcon(RESTART_GAME_ICON);
        add(restartGame);
    }

}
