package cc.vivp.bankrupt.exception;

public class CustomerAlreadyExistsException extends DomainException {

  private static final long serialVersionUID = 1_0_0L;
  private static final int STATUS = 409;

  public CustomerAlreadyExistsException(String message) {
    super(STATUS, message, null);
  }

  public CustomerAlreadyExistsException(String message, Throwable cause) {
    super(STATUS, message, cause);
  }
}
