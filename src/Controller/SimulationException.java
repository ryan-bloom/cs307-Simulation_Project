package Controller;

public class SimulationException extends RuntimeException {

    public SimulationException (String message){
        super(message);
    }

    public SimulationException (String message, Object values) {
        super(String.format(message, values));
    }

    public SimulationException (Throwable cause, String message, Object values) {
        super(String.format(message, values), cause);
    }
}
