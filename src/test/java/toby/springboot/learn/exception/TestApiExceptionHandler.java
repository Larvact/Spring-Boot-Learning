package toby.springboot.learn.exception;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class TestApiExceptionHandler
{
    @Autowired
    private ApiExceptionHandler apiExceptionHandler;

    @TestConfiguration
    static class ApiExceptionHandlerConfiguration
    {
        @Bean
        ApiExceptionHandler apiExceptionHandler()
        {
            return new ApiExceptionHandler();
        }
    }

    @Test
    void givenMalformedRequestException_handleBadRequest_responseEntityCorrect()
    {
        final var errorMessage = "The provided Id was missing. Unable to update the student.";
        final var malformedApiRequestException = new MalformedApiRequestException(errorMessage);

        final var responseEntity = apiExceptionHandler.handleBadRequest(malformedApiRequestException);

        assertThat(responseEntity)
                .has(new Condition<>(r -> r.getStatusCode() == HttpStatus.BAD_REQUEST, "Http response status code is 400"))
                .extracting(HttpEntity::getBody)
                .has(new Condition<>(errorDetail -> Objects.nonNull(errorDetail.getTimestamp()), "Timestamp has been generated"))
                .extracting(ApiResponseErrorDetail::getMessage, ApiResponseErrorDetail::getCause, ApiResponseErrorDetail::getHttpStatus)
                .containsExactly(errorMessage, null, HttpStatus.BAD_REQUEST);
    }
}