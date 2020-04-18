package cc.vivp.bankrupt.exception;

public class TransferException extends DomainException {

    private static final int STATUS = 500;

    public TransferException(String message) {
        super(STATUS, message, null);
    }

    public TransferException(String message, Throwable cause) {
        super(STATUS, message, cause);
    }

}
