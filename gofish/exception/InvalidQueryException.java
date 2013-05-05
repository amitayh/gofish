package gofish.exception;

import gofish.model.Player;

public class InvalidQueryException extends RuntimeException {
    
    private Player.Query query;

    public InvalidQueryException(Player.Query query) {
        this.query = query;
    }

}
