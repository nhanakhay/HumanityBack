package org.example.humanity.common.handlers;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.RequestInfo;
import org.example.humanity.common.exceptions.ResourceAlreadyExistsException;
import org.example.humanity.common.exceptions.ResourceNotFoundException;
import org.example.humanity.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
//    private final RequestInfo requestInfo;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Exception occurred while processing request: {}", ex.getMessage());
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred while processing request", LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(ResourceAlreadyExistsException e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
//
//    @ExceptionHandler(UserDoesNotExistException.class)
//    public ResponseEntity<ErrorResponse> handleUserDoesNotExistException(UserDoesNotExistException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage() , LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(CaptchaResponseInvalidException.class)
//    public ResponseEntity<ErrorResponse> handleCaptchaResponseInvalidException(CaptchaResponseInvalidException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(AuthenticationCustomException.class)
//    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationCustomException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(InterceptorException.class)
//    public ResponseEntity<ErrorResponse> handleInterceptorException(InterceptorException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
//    }

//    @ExceptionHandler(CustomAuthorizationException.class)
//    public ResponseEntity<ErrorResponse> handleCustomAuthorizationException(CustomAuthorizationException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
//    }
//    @ExceptionHandler(FileProcessingException.class)
//    public ResponseEntity<ErrorResponse> handleFileProcessingExceptionException(FileProcessingException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler(WalletValidityException.class)
//    public ResponseEntity<ErrorResponse> handleWalletValidityException(WalletValidityException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }
//    @ExceptionHandler(WalletInsufficientFundsException.class)
//    public ResponseEntity<ErrorResponse> handleWalletInsufficientFundsException(WalletInsufficientFundsException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.EXPECTATION_FAILED.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.EXPECTATION_FAILED);
//    }

//    @ExceptionHandler(AccessTokenInvalidCustomException.class)
//    public ResponseEntity<ErrorResponse> handleAccessTokenInvalidCustomException(AccessTokenInvalidCustomException e, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage(), LocalDateTime.now());
//        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
//    }

//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
//        String errorMessage = "File size exceeds the allowed limit.";
//        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
//    }

}
