package vr.authorizer.domain.balance;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vr.authorizer.domain.balance.exception.InsufficientFundsException;
import vr.authorizer.domain.balance.exception.InvalidBalanceAmountException;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.entity.CardBalance;
import vr.authorizer.infrastructure.persistence.repository.CardBalanceRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BalanceOperationService {

    private final CardBalanceRepository cardBalanceRepository;

    public CardBalance credit(Card card, BigDecimal amount) {
        validateAmount(amount);
        return cardBalanceRepository.save(CardBalance.credit(card, amount));
    }

    public CardBalance debit(Card card, BigDecimal amount) {
        validateAmount(amount);
        var isPositiveBalance = getBalance(card).compareTo(amount) > 0;

        if (!isPositiveBalance) throw new InsufficientFundsException();

        return cardBalanceRepository.save(CardBalance.credit(card, amount));
    }

    public BigDecimal getBalance(Card card) {
        var debitAmount = cardBalanceRepository.sumDebitAmountByCard(card);
        var creditAmount = cardBalanceRepository.sumCreditAmountByCard(card);
        return creditAmount.subtract(debitAmount);
    }

    private void validateAmount(BigDecimal amount) {
        var isPositiveValue = Optional.ofNullable(amount).orElseThrow(InvalidBalanceAmountException::new)
                .compareTo(BigDecimal.ZERO) >= 0;

        if (!isPositiveValue) throw new InvalidBalanceAmountException();
    }
}
