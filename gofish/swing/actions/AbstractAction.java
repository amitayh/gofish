package gofish.swing.actions;

import gofish.swing.SwingGame;

abstract public class AbstractAction extends javax.swing.AbstractAction {
    
    protected SwingGame game;

    public AbstractAction(SwingGame game, String name) {
        super(name);
        this.game = game;
    }
    
}
