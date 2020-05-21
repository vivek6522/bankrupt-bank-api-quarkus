package cc.vivp.bankrupt.exception;

public abstract class DomainException extends Exception {

  private static final long serialVersionUID = 1_0_0L;
  private final int status;

  public DomainException(int status, String message, Throwable cause) {
    super(message, cause);
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
