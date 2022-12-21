package vr.authorizer.domain.createcard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vr.authorizer.domain.balance.BalanceOperationService;
import vr.authorizer.domain.createcard.exception.ExistCardException;
import vr.authorizer.domain.createcard.exception.InvalidCardRequestException;
import vr.authorizer.domain.createcard.model.CardData;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.repository.CardRepository;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreateCardService {

    private final BigDecimal DEFAUL_BALANCE = new BigDecimal(500);
    private final CardRepository cardRepository;
    private final BalanceOperationService balanceOperationService;
    private final PasswordEncoder passwordEncoder;

    public Card process(CardData cardData) throws InvalidCardRequestException, ExistCardException {
        Optional.ofNullable(cardData).orElseThrow(InvalidCardRequestException::new);
        Optional.ofNullable(cardData.getNumber()).orElseThrow(InvalidCardRequestException::new);
        Optional.ofNullable(cardData.getPassword()).orElseThrow(InvalidCardRequestException::new);

        var card = cardRepository.findCardByNumber(cardData.getNumber());
        card.ifPresent(e -> {
            throw new ExistCardException(cardData);
        });

        var newCard = cardRepository.save(Card.fromNumberAndPassword(cardData.getNumber(), passwordEncoder.encode(cardData.getPassword())));
        balanceOperationService.credit(newCard, DEFAUL_BALANCE);
        return newCard;
    }
}
