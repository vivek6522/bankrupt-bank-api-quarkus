package cc.vivp.bankrupt.exception;

public class AccountCreationException extends DomainException {

  private static final long serialVersionUID = 1_0_0L;
  private static final int STATUS = 500;

  public AccountCreationException(String message) {
    super(STATUS, message, null);
  }

  public AccountCreationException(String message, Throwable cause) {
    super(STATUS, message, cause);
  }
}
