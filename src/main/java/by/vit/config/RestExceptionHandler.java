package by.vit.config;

import by.vit.dto.response.ErrorResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * class that provides centralized exception handling across all
 * {@code @RequestMapping} methods through {@code @ExceptionHandler} methods.
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
    private static final String SEMICOLON = ";";
    private static final String EMPTY = "";

    /**
     * Customize the response for MethodArgumentNotValidException.
     * <p>This method delegates to {@link #handleExceptionInternal}.
     * @param exception the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage().concat(SEMICOLON))
                .reduce(EMPTY, String::concat);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return new ResponseEntity(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Customize the response for ConstraintViolationException.
     *
     * @param exception the exception
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {
        LOGGER.error(exception.getMessage(), exception);
        String errorMessage = exception.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage().concat(SEMICOLON))
                .reduce(EMPTY, String::concat);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        return handleExceptionInternal(exception, errorResponseDTO, new HttpHeaders(), errorResponseDTO.getHttpStatus(), request);
    }

    /**
     * Customize the response for RuntimeException.
     *
     * @param exception the exception
     * @param request the current request
     * @return a {@code ResponseEntity} instance
     */
    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest request) {
        LOGGER.error(exception.getMessage(), exception);
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        return handleExceptionInternal(exception, errorResponseDTO, new HttpHeaders(), errorResponseDTO.getHttpStatus(), request);
    }
}
