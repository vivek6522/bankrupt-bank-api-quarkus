package cc.vivp.bankrupt.model.api;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import cc.vivp.bankrupt.model.AccountType;
import lombok.Data;

@Data
public class Account {

  @Schema(example = "12345678901234")
  String accountNumber;
  AccountType accountType;
  @Schema(example = "10,000.00")
  String balance;
  Boolean preferred;

}
