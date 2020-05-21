package cc.vivp.bankrupt.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import cc.vivp.bankrupt.exception.EntityNotFoundException;

public final class Generic {

  private Generic() {
    // Prohibit initialization. This class is supposed to be a utility class.
  }

  public static <T> T throwEntityNotFoundExceptionIfNotPresentElseReturnValue(T wrappedEntity)
      throws EntityNotFoundException {
    if (wrappedEntity != null) {
      return wrappedEntity;
    }
    throw new EntityNotFoundException(MessageKeys.NOT_FOUND);
  }

  public static Long convertToCents(final BigDecimal amount) {
    return amount.multiply(BigDecimal.valueOf(Constants.HUNDRED_CENTS)).longValue();
  }

  public static String convertFromCents(final Long amount) {
    return new DecimalFormat(Constants.DECIMAL_FORMAT).format(BigDecimal
    .valueOf(amount).divide(BigDecimal.valueOf(Constants.HUNDRED_CENTS), 2, RoundingMode.DOWN));
  }
}
