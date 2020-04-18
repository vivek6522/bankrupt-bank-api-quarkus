package cc.vivp.bankrupt.model.db;

import java.time.LocalDateTime;
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

@Entity(name = "Transfer")
@Table(name = "transfers")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {

    public TransferEntity(String paymentReference, String source, Long amount, String target, String description,
        LocalDateTime timestamp) {
        this(null, paymentReference, source, amount, target, description, timestamp);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "payment_reference")
    String paymentReference;
    @Column(name = "source")
    String source;
    @Column(name = "amount")
    Long amount;
    @Column(name = "target")
    String target;
    @Column(name = "description")
    String description;
    @Column(name = "timestamp")
    LocalDateTime timestamp;
}
