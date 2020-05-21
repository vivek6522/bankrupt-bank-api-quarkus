package cc.vivp.bankrupt.event;

import java.time.LocalDateTime;

import cc.vivp.bankrupt.exception.DomainException;

public abstract class DomainEvent<T> {

  protected static final String LOG_START = "[occured={}];[recorded={}];";
  protected LocalDateTime occurred;
  protected LocalDateTime recorded;

  public DomainEvent(final LocalDateTime occurred) {
    this.occurred = occurred;
    this.recorded = LocalDateTime.now();
  }

  public abstract T process() throws DomainException;
}
