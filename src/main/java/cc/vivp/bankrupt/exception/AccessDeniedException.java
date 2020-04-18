package cc.vivp.bankrupt.exception;

public class AccessDeniedException extends DomainException {

    private static final int STATUS = 403;

    public AccessDeniedException(String message) {
        super(STATUS, message, null);
    }

    public AccessDeniedException(String message, Throwable cause) {
        super(STATUS, message, cause);
    }
}
