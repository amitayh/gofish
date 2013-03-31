package gofish.exception;

public class CardCollisionException extends ConfigValidationException {

    private String name;

    public CardCollisionException(String name) {
        super("Card with name '" + name + "' was added twice");
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
