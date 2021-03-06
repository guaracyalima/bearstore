package com.hibicode.bearstore.error;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private static final String NO_MESSAGE_AVAILABLE = "No message available";
    private static final Logger LOG  = LoggerFactory.getLogger(ApiExceptionHandler.class);
    private final MessageSource apiErrorMessageSource;


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException manve, Locale locale){
        Stream<ObjectError> errors = manve.getBindingResult().getAllErrors().stream();
        List<ApiError> apiErrors = errors
                                        .map(ObjectError::getDefaultMessage)
                                        .map(code -> toApiError(code, locale))
                                        .collect(Collectors.toList());
        ErrorResponse erroResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, apiErrors);
        return  ResponseEntity.badRequest().body(erroResponse);
    }

    private ApiError toApiError(String code, Locale locale){
        String message;
        try {
            message = apiErrorMessageSource.getMessage(code, null, locale);
        } catch (NoSuchMessageException e){
            LOG.error("Could not find any message for {} code under {} locale", code, locale);
            message = NO_MESSAGE_AVAILABLE;
        }

        return new ApiError(code, message);
    }

}
