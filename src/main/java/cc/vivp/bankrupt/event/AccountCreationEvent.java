package cc.vivp.bankrupt.event;

import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;

import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.model.AccountType;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.repository.AccountsRepository;
import cc.vivp.bankrupt.util.Constants;
import cc.vivp.bankrupt.util.MessageKeys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountCreationEvent extends DomainEvent<Account> {

  private final AccountCommand accountCommand;
  private final String loggedInCustomerId;
  private final AccountsRepository accountsRepository;
  private final ModelMapper modelMapper;

  public AccountCreationEvent(LocalDateTime occurred, AccountCommand accountCommand, String loggedInCustomerId,
      AccountsRepository accountsRepository, ModelMapper modelMapper) {
    super(occurred);
    this.accountCommand = accountCommand;
    this.accountsRepository = accountsRepository;
    this.modelMapper = modelMapper;
    this.loggedInCustomerId = loggedInCustomerId;
  }

  @Override
  public Account process() throws AccountCreationException {

    if (!loggedInCustomerId.equals(accountCommand.getCustomerId())) {
      throw new AccountCreationException(MessageKeys.ORPHAN_ACCOUNT);
    }

    boolean isPreferredAccountPresent = accountsRepository.find("customerId = ?1", accountCommand.getCustomerId())
        .stream().anyMatch(account -> AccountType.CURRENT == account.getAccountType());

    AccountEntity newAccount = new AccountEntity(RandomStringUtils.randomNumeric(Constants.ACCOUNT_NUMBER_LENGTH),
        accountCommand.getAccountType(), Constants.DEFAULT_BALANCE_CENTS, accountCommand.getCustomerId(),
        !isPreferredAccountPresent);

    accountsRepository.persist(newAccount);
    log.info(LOG_START + "[accountNumber={}];[accountType={}];[customerId={}];", occurred, recorded,
        newAccount.getAccountNumber(), newAccount.getAccountType(), accountCommand.getCustomerId());

    return modelMapper.map(newAccount, Account.class);
  }
}
