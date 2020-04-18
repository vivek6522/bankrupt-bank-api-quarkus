package cc.vivp.bankrupt.model.db;

import cc.vivp.bankrupt.model.AccountType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "Account")
@Table(name = "accounts")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {

    public AccountEntity(final String accountNumber, final AccountType accountType, final Long balance,
        final String customerId) {
        this(null, accountNumber, accountType, balance, customerId, false);
    }

    public AccountEntity(final String accountNumber, final AccountType accountType, final Long balance,
        final String customerId, final Boolean preferred) {
        this(null, accountNumber, accountType, balance, customerId, preferred);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "account_number")
    String accountNumber;
    @Column(name = "account_type")
    AccountType accountType;
    Long balance;
    @Column(name = "customer_id")
    String customerId;
    @Column(name = "preferred")
    Boolean preferred;
}
