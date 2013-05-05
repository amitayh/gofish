package gofish.swing;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {
    
    final private static String NEW_GAME_ICON = "page_add.png";
    
    final private static String STOP_GAME_ICON = "page_delete.png";
    
    final private static String RESTART_GAME_ICON = "page_copy.png";
    
    private SwingGame game;
    
    public ToolBar(SwingGame game) {
        this.game = game;
        buildToolbar();
    }

    private void buildToolbar() {
        Icon newGameIcon = SwingUtils.getIcon(NEW_GAME_ICON);
        JButton newGame = new JButton(game.getNewGameAction());
        newGame.setIcon(newGameIcon);
        add(newGame);
        
        Icon stopGameIcon = SwingUtils.getIcon(STOP_GAME_ICON);
        JButton stopGame = new JButton(game.getStopGameAction());
        stopGame.setIcon(stopGameIcon);
        add(stopGame);
        
        Icon restartGameIcon = SwingUtils.getIcon(RESTART_GAME_ICON);
        JButton restartGame = new JButton(game.getRestartGameAction());
        restartGame.setIcon(restartGameIcon);
        add(restartGame);
    }

}
