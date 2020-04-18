package cc.vivp.bankrupt.event;

import cc.vivp.bankrupt.exception.DomainException;
import java.time.LocalDateTime;

public abstract class DomainEvent<T> {

    protected LocalDateTime occurred;
    protected LocalDateTime recorded;

    public DomainEvent(final LocalDateTime occurred) {
        this.occurred = occurred;
        this.recorded = LocalDateTime.now();
    }

    public abstract T process() throws DomainException;
}
