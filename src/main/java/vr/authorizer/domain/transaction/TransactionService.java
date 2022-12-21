package vr.authorizer.domain.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vr.authorizer.domain.balance.BalanceOperationService;
import vr.authorizer.domain.transaction.exception.CardNotFoundException;
import vr.authorizer.infrastructure.persistence.repository.CardRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CardRepository cardRepository;
    private final BalanceOperationService balanceOperationService;

    public BigDecimal obtainBalance(String cardNumber) {
        var card = cardRepository.findCardByNumber(cardNumber)
                .orElseThrow(() -> new CardNotFoundException(cardNumber));
        return balanceOperationService.getBalance(card);
    }
}
