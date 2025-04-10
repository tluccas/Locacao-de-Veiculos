package model.exceptions;

public class SenhaOuUserIncorretoException extends RuntimeException {
    public SenhaOuUserIncorretoException(String message) {
        super(message);
    }
}
