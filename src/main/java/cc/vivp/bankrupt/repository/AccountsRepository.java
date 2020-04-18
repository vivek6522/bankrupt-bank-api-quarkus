package cc.vivp.bankrupt.repository;

import cc.vivp.bankrupt.model.db.AccountEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountsRepository implements PanacheRepository<AccountEntity> {

}
