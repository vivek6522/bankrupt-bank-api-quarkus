package cc.vivp.bankrupt.model.api;

import cc.vivp.bankrupt.model.AccountType;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class Account {

  String accountNumber;
  AccountType accountType;
  String balance;
  Boolean preferred;

}
