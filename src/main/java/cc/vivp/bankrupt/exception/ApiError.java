package cc.vivp.bankrupt.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ApiError {

    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
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
