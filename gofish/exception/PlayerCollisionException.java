package gofish.exception;

public class PlayerCollisionException extends ConfigValidationException {
    
    private String name;

    public PlayerCollisionException(String name) {
        super("Player with name '" + name + "' was added twice");
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
