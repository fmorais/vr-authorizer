package vr.authorizer.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vr.authorizer.infrastructure.persistence.entity.Card;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {

    Optional<Card> findCardByNumber(String number);
}
