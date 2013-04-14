package gofish.swing.player;

import gofish.model.Player;
import gofish.swing.GameBoardPanel;

abstract public class AbstractPlayer extends Player {
    
    private GameBoardPanel gameBoard;

    public AbstractPlayer(Type type, String name) {
        super(type, name);
    }
    
    public void setGameBoard(GameBoardPanel gameBoard) {
        this.gameBoard = gameBoard;
    }

    public GameBoardPanel getGameBoard() {
        return gameBoard;
    }

}
