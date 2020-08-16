package cc.vivp.bankrupt.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import cc.vivp.bankrupt.exception.AccountCreationException;
import cc.vivp.bankrupt.exception.ApiError;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.Account;
import cc.vivp.bankrupt.model.api.AccountCommand;
import cc.vivp.bankrupt.service.AccountService;
import io.quarkus.security.Authenticated;

@Path("/accounts")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AccountsResource {

  @Inject
  AccountService accountService;
  @Claim(standard = Claims.sub)
  String customerId;

  @Tag(name = "accounts")
  @Operation(summary = "Get account details for a given account")
  @APIResponses(
    value = {
      @APIResponse(
        responseCode = "200",
        description = "Account details for the requested account",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(implementation = Account.class)
        )
      ),
      @APIResponse(
        responseCode = "404",
        description = "Account details not found for the requested account",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(implementation = ApiError.class)
        )
      )
    }
  )
  @GET
  @Path("{accountNumber}")
  @Metered(name = "accountDetailsFetchedPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  public Account fetchAccountDetails(@PathParam("accountNumber") String accountNumber) throws EntityNotFoundException {
    return accountService.fetchAccountDetails(accountNumber, customerId);
  }

  @Tag(name = "accounts")
  @Operation(summary = "Get account details for all owned accounts")
  @APIResponses(
    value = {
      @APIResponse(
        responseCode = "200",
        description = "Account details for all owned accounts",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(type = SchemaType.ARRAY, implementation = Account.class)
        )
      ),
      @APIResponse(
        responseCode = "404",
        description = "No accounts exist yet",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(implementation = ApiError.class)
        )
      )
    }
  )
  @GET
  @Path("self")
  @Metered(name = "selfAccountsFetchedPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  public List<Account> fetchAllAccountDetailsForSelf() throws EntityNotFoundException {
    return accountService.fetchAllAccountDetails(customerId);
  }

  @Tag(name = "accounts")
  @Operation(summary = "Create a new account")
  @APIResponses(
    value = {
      @APIResponse(
        responseCode = "200",
        description = "Account details for the newly created account",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(implementation = Account.class)
        )
      )
    }
  )
  @POST
  @Transactional
  @Metered(name = "accountCreationsPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  @Timed(name = "createAccountTimer", absolute = true, unit = MetricUnits.MILLISECONDS)
  public Account createAccount(AccountCommand accountCommand) throws AccountCreationException {
    return accountService.createAccount(accountCommand, customerId);
  }
}
