package kz.tmq.tmq_online_store.auth.exception;

import kz.tmq.tmq_online_store.auth.dto.HttpResponse;
import kz.tmq.tmq_online_store.auth.exception.auth.*;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
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

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<HttpResponse> roleNotFound(FormNotValidException exception) {
        return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<HttpResponse> emailExist(EmailExistException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UsernameExistException.class)
    public ResponseEntity<HttpResponse> usernameExist(UsernameExistException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(ActivationCodeNotFound.class)
    public ResponseEntity<HttpResponse> activationCodeNotFound(ActivationCodeNotFound exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<HttpResponse> authenticaiton(AuthenticationException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFound(UserNotFoundException exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentials() {
        return createHttpResponse(HttpStatus.BAD_REQUEST, "Invalid Email/Username/Password. Please, try again");
    }

    @ExceptionHandler(PasswordResetCodeNotFound.class)
    public ResponseEntity<HttpResponse> passwordResetCodeNotFound(PasswordResetCodeNotFound exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(OAuth2NoSuchRegistrationId.class)
    public ResponseEntity<HttpResponse> noSuchRegistrationId(OAuth2NoSuchRegistrationId exception) {
        return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

}
