package vr.authorizer.domain.createcard.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import vr.authorizer.domain.createcard.exception.ExistCardException;
import vr.authorizer.domain.createcard.exception.InvalidCardRequestException;
import vr.authorizer.domain.createcard.model.CardData;

@ControllerAdvice
public class CreateCardAdvisor {

    @ResponseBody
    @ExceptionHandler(InvalidCardRequestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String invalidCardRequestExceptionHandler(InvalidCardRequestException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ExistCardException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    CardData existCardHandler(ExistCardException ex) {
        return ex.getCardData();
    }
}
