package kz.tmq.tmq_online_store.exception;

import kz.tmq.tmq_online_store.dto.HttpResponse;
import kz.tmq.tmq_online_store.constant.AuthConstant;
import kz.tmq.tmq_online_store.exception.auth.*;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        HttpResponse httpResponse = new HttpResponse(
                httpStatus.value(),
                httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),
                message
        );
        return new ResponseEntity<>(httpResponse, httpStatus);
    }

    @ExceptionHandler(FormNotValidException.class)
    public ResponseEntity<HttpResponse> formNotValid(FormNotValidException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotActivatedException.class)
    public ResponseEntity<HttpResponse> userNotActivated(UserNotActivatedException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<HttpResponse> emailExist(ResourceExistException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<HttpResponse> emailExist(ResourceNotFoundException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentials() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, AuthConstant.BAD_CREDENTIALS_MESSAGE);
    }

    @ExceptionHandler(OAuth2NoSuchRegistrationId.class)
    public ResponseEntity<HttpResponse> noSuchRegistrationId(OAuth2NoSuchRegistrationId exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDenied(AccessDeniedException exception) {
        return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> authentication(Exception exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

}