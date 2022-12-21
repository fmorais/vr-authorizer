package vr.authorizer.domain.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vr.authorizer.domain.balance.BalanceOperationService;
import vr.authorizer.domain.transaction.exception.AuthenticationException;
import vr.authorizer.domain.transaction.exception.CardNotFoundException;
import vr.authorizer.domain.transaction.model.CardTransaction;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.repository.CardRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardRepository cardRepository;
    private final BalanceOperationService balanceOperationService;
    private final PasswordEncoder passwordEncoder;

    public BigDecimal obtainBalance(String cardNumber) {
        var card = findCardByNumber(cardNumber);
        return balanceOperationService.getBalance(card);
    }

    public String doTransaction(CardTransaction cardTransaction) {
        var card = findCardByNumber(cardTransaction.getCardNumber());
        if (!passwordEncoder.matches(cardTransaction.getPassword(), card.getPassword()))
            throw new AuthenticationException();

        balanceOperationService.debit(card, cardTransaction.getAmount());
        return "Ok";
    }

    private Card findCardByNumber(String cardNumber) {
        return cardRepository.findCardByNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException(cardNumber));
    }
}
