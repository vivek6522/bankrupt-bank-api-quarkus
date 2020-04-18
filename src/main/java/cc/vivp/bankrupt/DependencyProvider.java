package cc.vivp.bankrupt;

import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.model.db.TransferEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.enterprise.inject.Produces;
import javax.ws.rs.ext.Provider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

@Provider
public class DependencyProvider {

    @Produces
    public ModelMapper modelMapper() {

        Converter<BigDecimal, Long> toCents = context -> context.getSource().multiply(BigDecimal.valueOf(100L))
            .longValue();
        Converter<Long, String> fromCents = context -> new DecimalFormat("###,###,###,###.00")
            .format(BigDecimal.valueOf(context.getSource())
                .divide(BigDecimal.valueOf(100L), 2, RoundingMode.DOWN));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(AccountEntity.class, Account.class)
            .addMappings(mapper -> mapper.using(fromCents).map(AccountEntity::getBalance, Account::setBalance));
        modelMapper.typeMap(Account.class, AccountEntity.class)
            .addMappings(mapper -> mapper.using(toCents).map(Account::getBalance, AccountEntity::setBalance));

        modelMapper.typeMap(TransferEntity.class, TransferReceipt.class)
            .addMappings(mapper -> mapper.using(fromCents).map(TransferEntity::getAmount, TransferReceipt::setAmount));

        return modelMapper;
    }
}
