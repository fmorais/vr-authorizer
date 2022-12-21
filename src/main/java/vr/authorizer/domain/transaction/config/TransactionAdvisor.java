package vr.authorizer.domain.transaction.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vr.authorizer.domain.transaction.exception.CardNotFoundException;

@Slf4j
@ControllerAdvice
public class TransactionAdvisor {

    @ResponseBody
    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(CardNotFoundException ex) {
        log.info(ex.getMessage());
        return "";
    }
}
