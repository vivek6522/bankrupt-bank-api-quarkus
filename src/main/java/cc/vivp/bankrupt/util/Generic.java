package cc.vivp.bankrupt.util;

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
}
