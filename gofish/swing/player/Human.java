package gofish.swing.player;

import gofish.Game;
import gofish.exception.PlayerQueryException;

public class Human extends AbstractPlayer {

    public Human(String name) {
        super(Type.HUMAN, name);
    }

    @Override
    public Query getQuery(Game game) throws PlayerQueryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
