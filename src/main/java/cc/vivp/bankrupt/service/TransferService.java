package cc.vivp.bankrupt.service;

import static cc.vivp.bankrupt.util.Generic.throwEntityNotFoundExceptionIfNotPresentElseReturnValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import cc.vivp.bankrupt.event.TransferEvent;
import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.TransferEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.repository.TransfersRepository;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor(onConstructor_ = { @Inject })
public class TransferService {

  private final AccountsRepository accountsRepository;
  private final TransfersRepository transfersRepository;
  private final ModelMapper modelMapper;

  public TransferReceipt initiateFundsTransfer(@Valid TransferCommand transferCommand) throws DomainException {
    return new TransferEvent(LocalDateTime.now(), transferCommand, accountsRepository, transfersRepository).process();
  }

  public TransferReceipt fetchByPaymentReferenceAndSource(@NotBlank String paymentReference, @NotBlank String source)
      throws EntityNotFoundException {

    TransferEntity foundTransfer = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
        transfersRepository.fetchByPaymentReferenceAndSource(paymentReference, source));

    return modelMapper.map(foundTransfer, TransferReceipt.class);
  }

  public List<TransferReceipt> fetchTransactionHistory(@NotBlank String source) {

    List<TransferReceipt> transactionHistory = new ArrayList<>();

    transfersRepository.fetchTransactionHistory(source).stream()
        .sorted(Comparator.comparing(TransferEntity::getTimestamp).reversed())
        .forEach(transferEntity -> transactionHistory.add(modelMapper.map(transferEntity, TransferReceipt.class)));

    return transactionHistory;
  }
}
