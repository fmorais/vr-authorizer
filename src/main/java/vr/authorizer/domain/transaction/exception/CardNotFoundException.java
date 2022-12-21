package vr.authorizer.domain.transaction.exception;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String card) {
        super(String.format("Card %s not found", card));
    }
}
