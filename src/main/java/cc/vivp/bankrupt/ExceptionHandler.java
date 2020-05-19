package cc.vivp.bankrupt;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import cc.vivp.bankrupt.exception.ApiError;
import cc.vivp.bankrupt.exception.DomainException;

@Provider
public class ExceptionHandler implements ExceptionMapper<DomainException> {

  @Override
  public Response toResponse(final DomainException e) {
    return Response.status(e.getStatus()).entity(new ApiError(e.getStatus(), e.getMessage())).build();
  }
}
