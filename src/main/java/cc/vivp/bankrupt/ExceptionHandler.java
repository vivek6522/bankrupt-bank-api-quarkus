package cc.vivp.bankrupt;

import cc.vivp.bankrupt.exception.ApiError;
import cc.vivp.bankrupt.exception.DomainException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(final DomainException e) {
        return Response.status(e.getStatus()).entity(new ApiError(e.getStatus(), e.getMessage())).build();
    }
}
