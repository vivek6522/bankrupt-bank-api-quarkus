package cc.vivp.bankrupt.util;

public final class Constants {

  public static final int ACCOUNT_NUMBER_LENGTH = 16;
  public static final long DEFAULT_BALANCE_CENTS = 0L;
  public static final long HUNDRED_CENTS = 100L;
  public static final String DECIMAL_FORMAT = "###,###,###,###.00";

  private Constants() {
    // Prohibit initialization. This class is supposed to contain only constants.
  }

}
