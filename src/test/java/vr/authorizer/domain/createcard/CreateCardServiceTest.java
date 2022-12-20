package vr.authorizer.domain.createcard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import vr.authorizer.domain.createcard.exception.ExistCardException;
import vr.authorizer.domain.createcard.exception.InvalidCardRequestException;
import vr.authorizer.domain.createcard.model.CardData;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.repository.CardRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(MockitoJUnitRunner.class)
public class CreateCardServiceTest {

    @Mock
    private CardRepository cardRepository;

    private CreateCardService createCardService;

    @Before
    public void setup() {
        createCardService = new CreateCardService(cardRepository);
    }

    @Test
    public void testInvalidPayload() {
        assertThrows(InvalidCardRequestException.class, () -> createCardService.process(null));
    }

    @Test
    public void testExistCard() {
        var cardData = new CardData("123456789", "123");
        Mockito.when(cardRepository.findCardByNumber(eq("123456789")))
                .thenReturn(Optional.of(new Card()));

        assertThrows(ExistCardException.class, () -> createCardService.process(cardData));
    }

    @Test
    public void testCreateCard() throws InvalidCardRequestException {
        var cardData = new CardData("123456789", "123");
        Mockito.when(cardRepository.findCardByNumber(eq("123456789")))
                .thenReturn(Optional.empty());

        createCardService.process(cardData);

        Mockito.verify(cardRepository).save(eq(Card.fromCardData(cardData)));
    }
}
