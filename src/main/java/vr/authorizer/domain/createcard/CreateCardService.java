package vr.authorizer.domain.createcard;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vr.authorizer.domain.createcard.exception.ExistCardException;
import vr.authorizer.domain.createcard.exception.InvalidCardRequestException;
import vr.authorizer.domain.createcard.model.CardData;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.repository.CardRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreateCardService {

    private final CardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;

    public Card process(CardData cardData) throws InvalidCardRequestException, ExistCardException {
        Optional.ofNullable(cardData).orElseThrow(InvalidCardRequestException::new);
        Optional.ofNullable(cardData.getNumber()).orElseThrow(InvalidCardRequestException::new);
        Optional.ofNullable(cardData.getPassword()).orElseThrow(InvalidCardRequestException::new);

        var card = cardRepository.findCardByNumber(cardData.getNumber());
        card.ifPresent(e -> {
            throw new ExistCardException(cardData);
        });

        return cardRepository.save(Card.fromNumberAndPassword(cardData.getNumber(), passwordEncoder.encode(cardData.getPassword())));
    }
}
