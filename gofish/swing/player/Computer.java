package gofish.swing.player;

import gofish.Game;
import gofish.exception.PlayerQueryException;
import gofish.model.Player;

public class Computer extends Player {

    public Computer(String name) {
        super(Type.COMPUTER, name);
    }

    @Override
    public Query getQuery(Game game) throws PlayerQueryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
