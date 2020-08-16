package cc.vivp.bankrupt.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import lombok.Getter;

@Getter
public class ApiError {

  @Schema(example = "404")
  private int status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime timestamp;
  @Schema(example = "messages.not_found")
  private String message;
  private List<ApiError> subErrors;

  public ApiError(int status) {
    this(status, "Unexpected error", new ArrayList<>());
  }

  public ApiError(int status, String message) {
    this(status, message, new ArrayList<>());
  }

  public ApiError(int status, String message, List<ApiError> subErrors) {
    timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.subErrors = subErrors;
  }

  public void addSubError(ApiError subError) {
    subErrors.add(subError);
  }
}
