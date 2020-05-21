package cc.vivp.bankrupt.resource;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import cc.vivp.bankrupt.exception.AccessDeniedException;
import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.exception.NoAccountsYetException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.service.AccountService;
import cc.vivp.bankrupt.util.MessageKeys;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import lombok.AllArgsConstructor;

@Path("/accounts")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class AccountsResource {

  final AccountService accountService;
  final SecurityIdentity identity;

  @GET
  @Path("{accountNumber}")
  @Metered(name = "accountDetailsFetchedPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  public Account fetchAccountDetails(@PathParam("accountNumber") String accountNumber,
      @Context SecurityContext securityContext) throws EntityNotFoundException, AccessDeniedException {
    Account requestedAccount = accountService.fetchAccountDetails(accountNumber,
        securityContext.getUserPrincipal().getName());
    if (requestedAccount != null) {
      return requestedAccount;
    }
    throw new AccessDeniedException(MessageKeys.ACCOUNT_NOT_OWNED);
  }

  @GET
  @Path("self")
  @Metered(name = "selfAccountsFetchedPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  public List<Account> fetchAllAccountDetailsForSelf(@Context SecurityContext securityContext)
      throws EntityNotFoundException, NoAccountsYetException {
    List<Account> allAccountDetails = accountService
        .fetchAllAccountDetails(securityContext.getUserPrincipal().getName());
    if (allAccountDetails.isEmpty()) {
      throw new NoAccountsYetException(MessageKeys.NO_ACCOUNTS_EXIST_YET);
    }
    return allAccountDetails;
  }

  @POST
  @Transactional
  @Metered(name = "accountCreationsPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  @Timed(name = "createAccountTimer", absolute = true, unit = MetricUnits.MILLISECONDS)
  public Account createAccount(AccountCommand accountCommand, @Context SecurityContext securityContext)
      throws AccountCreationException {
    return accountService.createAccount(accountCommand, securityContext.getUserPrincipal().getName());
  }
}
