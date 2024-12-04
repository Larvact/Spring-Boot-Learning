package toby.springboot.learn.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class ApiResponseErrorDetail
{
    private final String message;
    private final HttpStatus httpStatus;
    private final Throwable cause;
    private final Instant timestamp;

    public ApiResponseErrorDetail(final String message, final Throwable cause, final HttpStatus httpStatus)
    {
        this.message = message;
        this.cause = cause;
        this.httpStatus = httpStatus;
        this.timestamp = Instant.now();
    }
}
