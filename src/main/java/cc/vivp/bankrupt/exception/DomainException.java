package cc.vivp.bankrupt.exception;

public abstract class DomainException extends Exception {

    private final int status;

    public DomainException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
