package cc.vivp.bankrupt;

import static cc.vivp.bankrupt.util.Generic.convertFromCents;
import static cc.vivp.bankrupt.util.Generic.convertToCents;

import java.math.BigDecimal;

import javax.enterprise.inject.Produces;
import javax.ws.rs.ext.Provider;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.model.db.AccountEntity;
import cc.vivp.bankrupt.model.db.TransferEntity;

@Provider
public class DependencyProvider {

  @Produces
  public ModelMapper modelMapper() {

    Converter<BigDecimal, Long> toCents = context -> convertToCents(context.getSource());
    Converter<Long, String> fromCents = context -> convertFromCents(context.getSource());

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
