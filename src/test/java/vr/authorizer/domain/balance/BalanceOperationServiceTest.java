package vr.authorizer.domain.balance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.entity.CardBalance;
import vr.authorizer.infrastructure.persistence.repository.CardBalanceRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BalanceOperationServiceTest {

    @Mock
    private CardBalanceRepository cardBalanceRepository;

    private BalanceOperationService balanceOperationService;

    @Before
    public void setup() {
        balanceOperationService = new BalanceOperationService(cardBalanceRepository);
    }

    @Test
    public void testDebit() {
        var card = new Card("123456789", null);
        var saveCardBalance = CardBalance.debit(card, new BigDecimal(100));
        when(cardBalanceRepository.sumCreditAmountByCard(eq(card)))
                .thenReturn(new BigDecimal(500).setScale(2, RoundingMode.UNNECESSARY));
        when(cardBalanceRepository.sumDebitAmountByCard(eq(card)))
                .thenReturn(null);
        when(cardBalanceRepository.save(any()))
                .thenReturn(saveCardBalance);
        var cardBalance = balanceOperationService.debit(card, new BigDecimal(100));
        assertEquals(cardBalance.getType(), CardBalance.TransactionType.DEBIT);
        assertEquals(cardBalance.getAmount(), new BigDecimal(100));
    }

    @Test
    public void testCredit() {
        var card = new Card("123456789", null);
        var saveCardBalance = CardBalance.credit(card, new BigDecimal(100));
        when(cardBalanceRepository.save(any()))
                .thenReturn(saveCardBalance);
        var cardBalance = balanceOperationService.credit(card, new BigDecimal(100));
        assertEquals(cardBalance.getType(), CardBalance.TransactionType.CREDIT);
        assertEquals(cardBalance.getAmount(), new BigDecimal(100));
    }
}
