package gofish.swing;

import java.awt.FlowLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {
    
    final private static Icon NEW_GAME_ICON = SwingUtils.getIcon("page_add.png");
    
    final private static Icon STOP_GAME_ICON = SwingUtils.getIcon("page_delete.png");
    
    final private static Icon RESTART_GAME_ICON = SwingUtils.getIcon("page_copy.png");
    
    final private static int GAP = 5;
    
    private SwingGame game;
    
    public ToolBar(SwingGame game) {
        setLayout(new FlowLayout(FlowLayout.LEADING, GAP, GAP));
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
