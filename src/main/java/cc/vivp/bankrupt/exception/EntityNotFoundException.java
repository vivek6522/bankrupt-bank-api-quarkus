package cc.vivp.bankrupt.exception;

public class EntityNotFoundException extends DomainException {

  private static final int STATUS = 404;

  public EntityNotFoundException(String message) {
    super(STATUS, message, null);
  }

  public EntityNotFoundException(String message, Throwable cause) {
    super(STATUS, message, cause);
  }

}
