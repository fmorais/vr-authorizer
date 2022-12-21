package vr.authorizer.domain.createcard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vr.authorizer.domain.balance.BalanceOperationService;
import vr.authorizer.domain.createcard.exception.ExistCardException;
import vr.authorizer.domain.createcard.exception.InvalidCardRequestException;
import vr.authorizer.domain.createcard.model.CardData;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.repository.CardRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateCardServiceTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private BalanceOperationService balanceOperationService;
    private PasswordEncoder passwordEncoder;
    private CreateCardService createCardService;


    @Before
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
        createCardService = new CreateCardService(cardRepository, balanceOperationService, passwordEncoder);
    }

    @Test
    public void testInvalidPayload() {
        assertThrows(InvalidCardRequestException.class, () -> createCardService.process(null));
        assertThrows(InvalidCardRequestException.class, () -> createCardService.process(new CardData()));
        assertThrows(InvalidCardRequestException.class, () -> createCardService.process(new CardData(null, "password")));
        assertThrows(InvalidCardRequestException.class, () -> createCardService.process(new CardData("123", null)));
    }

    @Test
    public void testExistCard() {
        var cardData = new CardData("123456789", "123");
        when(cardRepository.findCardByNumber(eq("123456789")))
                .thenReturn(Optional.of(new Card()));

        assertThrows(ExistCardException.class, () -> createCardService.process(cardData));
    }

    @Test
    public void testCreateCard() throws InvalidCardRequestException {
        var cardData = new CardData("123456789", "123");
        when(cardRepository.findCardByNumber(eq("123456789")))
                .thenReturn(Optional.empty());
        when(cardRepository.save(eq(Card.fromNumberAndPassword("123456789", "123"))))
                .thenReturn(new Card(cardData.getNumber(), passwordEncoder.encode(cardData.getPassword())));
        var persistedData = createCardService.process(cardData);

        Mockito.verify(cardRepository).save(eq(Card.fromNumberAndPassword("123456789", "123")));
        Mockito.verify(balanceOperationService).credit(persistedData, new BigDecimal(500));
        assertAll(() -> assertEquals(cardData.getNumber(), persistedData.getNumber()),
                () -> assertTrue(passwordEncoder.matches(cardData.getPassword(), persistedData.getPassword())));
    }
}
