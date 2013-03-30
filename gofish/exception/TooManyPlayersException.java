package gofish.exception;

public class TooManyPlayersException extends ConfigValidationException {

    private int max;
    
    private int actual;

    public TooManyPlayersException(int max, int actual) {
        super("Too many players (maximum: " + max + ", actual: " + actual + ")");
        this.max = max;
        this.actual = actual;
    }

    public int getMax() {
        return max;
    }
    
    public int getActual() {
        return actual;
    }

}
