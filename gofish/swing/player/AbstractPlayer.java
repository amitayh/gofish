package gofish.swing.player;

import gofish.model.Player;
import gofish.swing.GameBoardPanel;
import gofish.swing.SwingGame;

abstract public class AbstractPlayer extends Player {
    
    private SwingGame game;

    public AbstractPlayer(Type type, String name) {
        super(type, name);
    }

    public SwingGame getGame() {
        return game;
    }

    public void setGame(SwingGame game) {
        this.game = game;
    }

}