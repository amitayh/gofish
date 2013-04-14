package gofish.swing.player;

import gofish.Game;
import gofish.exception.PlayerQueryException;

public class Computer extends AbstractPlayer {

    public Computer(String name) {
        super(Type.COMPUTER, name);
    }

    @Override
    public Query getQuery(Game game) throws PlayerQueryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
