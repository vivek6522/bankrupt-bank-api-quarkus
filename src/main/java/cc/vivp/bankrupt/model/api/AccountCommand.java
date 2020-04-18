package cc.vivp.bankrupt.model.api;

import cc.vivp.bankrupt.model.AccountType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountCommand {

    @NotNull
    AccountType accountType;
    @NotBlank
    String customerId;

}
