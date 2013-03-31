package gofish.exception;

public class TooFewCardsException extends ConfigValidationException {
    
    private int min;
    
    private int actual;

    public TooFewCardsException(int min, int actual) {
        super("Not enough cards (minimum: " + min + ", actual: " + actual + ")");
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
