package ch.uzh.ifi.seal.ase.mrs.memberservice.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
/**
 * Test GeneralWebserviceException
 */
public class GeneralWebserviceExceptionTest {

    /**
     * Test the GeneralWebserviceException Builder and Getters
     */
    @Test
    void testGeneralWebserviceException() {
        GeneralWebserviceException exception = GeneralWebserviceException.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Message")
                .errorCode("CODE001")
                .details("Details")
                .trace("Trace").build();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals("Message", exception.getMessage());
        Assertions.assertEquals("CODE001", exception.getErrorCode());
        Assertions.assertEquals("Details", exception.getDetails());
        Assertions.assertEquals("Trace", exception.getTrace());
    }
}
