package ch.uzh.ifi.seal.ase.mrs.freeservice.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * Test GeneralWebserviceExceptionSchema
 */
public class GeneralWebserviceExceptionSchemaTest {

    /**
     * Test the GeneralWebserviceExceptionSchema Builder and Getters
     */
    @Test
    void testGeneralWebserviceExceptionSchema() {
        GeneralWebserviceExceptionSchema exception = GeneralWebserviceExceptionSchema.builder()
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
