package vr.authorizer.domain.createcard.exception;

import lombok.Getter;
import vr.authorizer.domain.createcard.model.CardData;

public class ExistCardException extends RuntimeException {

    @Getter
    private final CardData cardData;

    public ExistCardException(CardData cardData) {
        this.cardData = cardData;
    }
}
