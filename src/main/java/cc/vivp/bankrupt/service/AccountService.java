package cc.vivp.bankrupt.service;

import static cc.vivp.bankrupt.util.Generic.throwEntityNotFoundExceptionIfNotPresentElseReturnValue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.modelmapper.ModelMapper;

import cc.vivp.bankrupt.event.AccountCreationEvent;
import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor(onConstructor_ = { @Inject })
public class AccountService {

  private final AccountsRepository accountsRepository;
  private final ModelMapper modelMapper;

  public Account createAccount(@Valid AccountCommand accountCommand, @NotBlank String loggedInCustomerId)
      throws AccountCreationException {
    return new AccountCreationEvent(LocalDateTime.now(), accountCommand, loggedInCustomerId, accountsRepository,
        modelMapper).emit();
  }

  public Account fetchAccountDetails(@NotBlank String accountNumber, @NotBlank String customerId)
      throws EntityNotFoundException {
    AccountEntity requestedAccount = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
        accountsRepository.find("accountNumber = ?1 and customerId = ?2", accountNumber, customerId).firstResult());
    return modelMapper.map(requestedAccount, Account.class);
  }

  public List<Account> fetchAllAccountDetails(@NotBlank String customerId) throws EntityNotFoundException {
    List<AccountEntity> allAccountsForCustomer = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
        accountsRepository.find("customerId = ?1", customerId).list());
    return allAccountsForCustomer.stream().map(accountEntity -> modelMapper.map(accountEntity, Account.class))
        .collect(Collectors.toUnmodifiableList());
  }

}
