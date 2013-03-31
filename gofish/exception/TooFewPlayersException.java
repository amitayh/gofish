package gofish.exception;

public class TooFewPlayersException extends ConfigValidationException {

    private int min;
    
    private int actual;

    public TooFewPlayersException(int min, int actual) {
        super("Not enough players (minimum: " + min + ", actual: " + actual + ")");
        this.min = min;
        this.actual = actual;
    }

    public int getMin() {
        return min;
    }
    
    public int getActual() {
        return actual;
    }

}
