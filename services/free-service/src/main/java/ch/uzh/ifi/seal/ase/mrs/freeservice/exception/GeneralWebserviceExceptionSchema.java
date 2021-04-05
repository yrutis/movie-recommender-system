package ch.uzh.ifi.seal.ase.mrs.freeservice.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom Exception Schema Class for the Webservice, serves as DTO to the frontend
 */
@Builder
@Getter
public class GeneralWebserviceExceptionSchema {
    /**
     * Error Message
     */

    private final String message;

    /**
     * Error Details
     */
    private final String details;

    /**
     * Error Code
     */
    private final String errorCode;

    /**
     * Error Trace
     */
    private final String trace;

    /**
     * Error HttpStatus Code
     */
    private final HttpStatus status;
}