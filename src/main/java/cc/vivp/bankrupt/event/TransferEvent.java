package cc.vivp.bankrupt.event;

import static cc.vivp.bankrupt.util.Generic.convertFromCents;
import static cc.vivp.bankrupt.util.Generic.convertToCents;
import static cc.vivp.bankrupt.util.Generic.throwEntityNotFoundExceptionIfNotPresentElseReturnValue;

import java.time.LocalDateTime;
import java.util.UUID;

import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.exception.TransferException;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.model.db.TransferEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.TransfersRepository;
import cc.vivp.bankrupt.util.MessageKeys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferEvent extends DomainEvent<TransferReceipt> {

  private final TransferCommand transferCommand;
  private final AccountsRepository accountsRepository;
  private final TransfersRepository transfersRepository;

  public TransferEvent(final LocalDateTime occurred, final TransferCommand transferCommand,
      final AccountsRepository accountsRepository, final TransfersRepository transfersRepository) {
    super(occurred);
    this.transferCommand = transferCommand;
    this.accountsRepository = accountsRepository;
    this.transfersRepository = transfersRepository;
  }

  @Override
  public TransferReceipt process() throws DomainException {

    AccountEntity source;
    AccountEntity target;
    try {

      source = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
          accountsRepository.find("accountNumber", transferCommand.getSource()).firstResult());

      target = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
          accountsRepository.find("accountNumber", transferCommand.getTarget()).firstResult());

    } catch (EntityNotFoundException e) {
      log.error("Account not found.", e);
      throw new TransferException(MessageKeys.ACCOUNT_NOT_FOUND);
    }

    long transferAmount = convertToCents(transferCommand.getAmount());
    source.setBalance(source.getBalance() - transferAmount);
    target.setBalance(target.getBalance() + transferAmount);

    String paymentReference = UUID.randomUUID().toString();

    accountsRepository.persist(source);
    TransferEntity transferEntity = new TransferEntity(paymentReference, source.getAccountNumber(),
        Math.negateExact(transferAmount), target.getAccountNumber(), transferCommand.getDescription(), recorded);
    transfersRepository.persist(transferEntity);
    log.info(LOG_START + "[paymentReference={}];[source={}];[amount={}];[target={}];[description={}];", occurred,
        recorded, paymentReference, transferCommand.getSource(), Math.negateExact(transferAmount),
        transferCommand.getTarget(), transferCommand.getDescription());

    accountsRepository.persist(target);
    transferEntity = new TransferEntity(paymentReference, target.getAccountNumber(), transferAmount,
        source.getAccountNumber(), transferCommand.getDescription(), recorded);
    transfersRepository.persist(transferEntity);
    log.info(LOG_START + "[paymentReference={}];[source={}];[amount={}];[target={}];[description={}];", occurred,
        recorded, paymentReference, transferCommand.getTarget(), transferAmount, transferCommand.getSource(),
        transferCommand.getDescription());

    return new TransferReceipt(paymentReference, source.getAccountNumber(), convertFromCents(-transferAmount),
        transferCommand.getTarget(), transferCommand.getDescription(), recorded);
  }

}
