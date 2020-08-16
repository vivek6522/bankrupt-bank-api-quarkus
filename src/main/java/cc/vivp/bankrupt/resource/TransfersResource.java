package cc.vivp.bankrupt.resource;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import cc.vivp.bankrupt.exception.ApiError;
import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.service.TransferService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import lombok.AllArgsConstructor;

@Path("/transfers")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
@RequestScoped
public class TransfersResource {

  final TransferService transferService;
  final SecurityIdentity identity;
  final JsonWebToken jwt;

  @Tag(name = "transfers")
  @Operation(summary = "Initiate a funds transfer")
  @RequestBody(
    content = @Content(
      mediaType = MediaType.APPLICATION_JSON,
      schema = @Schema(implementation = TransferCommand.class)
    )
  )
  @APIResponses(
    value = {
      @APIResponse(
        responseCode = "200",
        description = "Transfer funds to a beneficiary",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(implementation = TransferReceipt.class)
        )
      )
    }
  )
  @POST
  @Transactional
  @Metered(name = "fundTransfersPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  @Timed(name = "initiateFundsTransferTimer", absolute = true, unit = MetricUnits.MILLISECONDS)
  public TransferReceipt initiateFundsTransfer(@Valid TransferCommand transferCommand) throws DomainException {
    return transferService.initiateFundsTransfer(transferCommand);
  }

  @Tag(name = "transfers")
  @Operation(summary = "Get details for a given payment reference")
  @APIResponses(
    value = {
      @APIResponse(
        responseCode = "200",
        description = "Payment details for the requested payment reference",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(implementation = TransferReceipt.class)
        )
      ),
      @APIResponse(
        responseCode = "404",
        description = "Payment details not found for the requested payment reference",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(implementation = ApiError.class)
        )
      )
    }
  )
  @GET
  @Path("reference/{paymentReference}/{source}")
  public TransferReceipt fetchByPaymentReferenceAndSource(@PathParam("paymentReference") String paymentReference,
      @PathParam("source") String source) throws EntityNotFoundException {
    return transferService.fetchByPaymentReferenceAndSource(paymentReference, source);
  }

  @Tag(name = "transfers")
  @Operation(summary = "Get transaction history for a given account")
  @APIResponses(
    value = {
      @APIResponse(
        responseCode = "200",
        description = "Transaction history for the requested account",
        content = @Content(
          mediaType = MediaType.APPLICATION_JSON,
          schema = @Schema(type = SchemaType.ARRAY, implementation = TransferReceipt.class)
        )
      )
    }
  )
  @GET
  @Path("account/{source}")
  @Metered(name = "transactionHistoriesFetchedPerSecond", unit = MetricUnits.SECONDS, absolute = true)
  public List<TransferReceipt> fetchTransactionHistory(@PathParam("source") String source,
      @Context SecurityContext securityContext) {
    return transferService.fetchTransactionHistory(source);
  }
}
