package toby.springboot.learn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler
{
    @ExceptionHandler(MalformedApiRequestException.class)
    public ResponseEntity<ApiResponseErrorDetail> handleBadRequest(final Exception exception)
    {
        final var errorDetail = new ApiResponseErrorDetail(exception.getMessage(), exception.getCause(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }
}
