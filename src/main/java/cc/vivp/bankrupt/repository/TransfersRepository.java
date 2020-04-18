package cc.vivp.bankrupt.repository;

import cc.vivp.bankrupt.model.db.TransferEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransfersRepository implements PanacheRepository<TransferEntity> {

    public TransferEntity fetchByPaymentReferenceAndSource(String paymentReference,
        String source) {
        return find("paymentReference = ?1 and source = ?2", paymentReference, source).firstResult();
    }

    public List<TransferEntity> fetchTransactionHistory(String source) {
        return find("source = ?1", source).list();
    }

}
