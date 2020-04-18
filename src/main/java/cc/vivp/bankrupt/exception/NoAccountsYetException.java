package cc.vivp.bankrupt.exception;

public class NoAccountsYetException extends DomainException {

    private static final int STATUS = 204;

    public NoAccountsYetException(String message) {
        super(STATUS, message, null);
    }

    public NoAccountsYetException(String message, Throwable cause) {
        super(STATUS, message, cause);
    }
}
