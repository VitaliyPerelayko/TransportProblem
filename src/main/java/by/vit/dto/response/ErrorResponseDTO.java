package by.vit.dto.response;

import org.springframework.http.HttpStatus;

/**
 * DTO for response errors
 */
public class ErrorResponseDTO {

    private HttpStatus httpStatus;

    private String message;

    public ErrorResponseDTO(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
