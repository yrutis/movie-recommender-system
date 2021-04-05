package ch.uzh.ifi.seal.ase.mrs.memberservice.config;

import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.memberservice.exception.GeneralWebserviceExceptionSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test ExceptionInterceptor
 */
public class ExceptionInterceptorTest {
    /**
     * Test handleAllExceptions
     */
    @Test
    public void handleAllExceptions() {
        ExceptionInterceptor exceptionInterceptor = new ExceptionInterceptor();
         GeneralWebserviceException exception = GeneralWebserviceException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Message")
                .errorCode("CODE001")
                .details("Details")
                .trace("Trace").build();
        ResponseEntity<GeneralWebserviceExceptionSchema> response = exceptionInterceptor.handleAllExceptions(exception);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getBody().getStatus());
        Assertions.assertEquals("Message", response.getBody().getMessage());
        Assertions.assertEquals("CODE001", response.getBody().getErrorCode());
        Assertions.assertEquals("Details", response.getBody().getDetails());
        Assertions.assertEquals("Trace", response.getBody().getTrace());
    }
}
