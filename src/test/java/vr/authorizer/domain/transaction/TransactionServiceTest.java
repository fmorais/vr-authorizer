package vr.authorizer.domain.transaction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import vr.authorizer.domain.balance.BalanceOperationService;
import vr.authorizer.domain.transaction.exception.CardNotFoundException;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.repository.CardRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private BalanceOperationService balanceOperationService;
    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionService = new TransactionService(cardRepository, balanceOperationService);
    }

    @Test
    public void testObtainBalance() {
        var card = new Card("123456789", null);
        var defaultBalance = new BigDecimal(500).setScale(2, RoundingMode.UNNECESSARY);

        when(cardRepository.findCardByNumber(eq("123456789")))
                .thenReturn(Optional.of(card));
        when(balanceOperationService.getBalance(eq(card)))
                .thenReturn(defaultBalance);

        var balance = transactionService.obtainBalance("123456789");
        assertEquals(balance, defaultBalance);
    }

    @Test
    public void testCardBalanceNotFound() {
        when(cardRepository.findCardByNumber(eq("123456789")))
                .thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> transactionService.obtainBalance("123456789"));
    }
}
