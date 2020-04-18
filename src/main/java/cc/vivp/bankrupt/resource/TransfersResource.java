package cc.vivp.bankrupt.resource;

import cc.vivp.bankrupt.exception.DomainException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.TransferCommand;
import cc.vivp.bankrupt.model.api.TransferReceipt;
import cc.vivp.bankrupt.service.TransferService;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/transfers")
@Authenticated
@RolesAllowed("bankrupt-customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class TransfersResource {

    final TransferService transferService;
    final SecurityIdentity identity;
    final JsonWebToken jwt;

    @POST
    @Transactional
    public TransferReceipt initiateFundsTransfer(@Valid TransferCommand transferCommand) throws DomainException {
        return transferService.initiateFundsTransfer(transferCommand);
    }

    @GET
    @Path("reference/{paymentReference}/{source}")
    public TransferReceipt fetchByPaymentReferenceAndSource(@PathParam("paymentReference") String paymentReference,
        @PathParam("source") String source)
        throws EntityNotFoundException {
        return transferService.fetchByPaymentReferenceAndSource(paymentReference, source);
    }

    @GET
    @Path("account/{source}")
    public List<TransferReceipt> fetchTransactionHistory(@PathParam("source") String source,
        @Context SecurityContext securityContext) {
        return transferService.fetchTransactionHistory(source);
    }
}
