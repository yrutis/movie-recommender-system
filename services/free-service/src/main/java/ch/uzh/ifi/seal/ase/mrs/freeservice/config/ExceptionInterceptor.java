package ch.uzh.ifi.seal.ase.mrs.freeservice.config;


import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceException;
import ch.uzh.ifi.seal.ase.mrs.freeservice.exception.GeneralWebserviceExceptionSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Controller Advice to intercept all exceptions and wrap them in a GeneralWebserviceException
 */
@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

    /**
     * Handles all GeneralWebserviceExceptions by wrapping them into a GeneralWebserviceExceptionSchema
     * @param ex GeneralWebserviceException
     * @return ResponseEntity containing the exception wrapped into the GeneralWebserviceExceptionSchema
     */
    @ExceptionHandler(GeneralWebserviceException.class)
    public final ResponseEntity<GeneralWebserviceExceptionSchema> handleAllExceptions(GeneralWebserviceException ex) {
        GeneralWebserviceExceptionSchema exceptionResponse =
                GeneralWebserviceExceptionSchema.builder().message(ex.getMessage()).details(ex.getDetails()).errorCode(ex.getErrorCode()).trace(ex.getTrace()).status(ex.getStatus()).build();
        return new ResponseEntity<>(exceptionResponse, ex.getStatus());
    }
}