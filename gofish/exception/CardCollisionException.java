package gofish.exception;

public class CardCollisionException extends RuntimeException {

    public CardCollisionException(String name) {
        super("Card with name '" + name + "' was added twice");
    }

}
