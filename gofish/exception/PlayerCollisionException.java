package gofish.exception;

public class PlayerCollisionException extends RuntimeException {

    public PlayerCollisionException(String name) {
        super("Player with name '" + name + "' was added twice");
    }

}
