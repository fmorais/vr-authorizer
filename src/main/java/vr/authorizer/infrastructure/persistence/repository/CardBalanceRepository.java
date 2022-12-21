package vr.authorizer.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vr.authorizer.infrastructure.persistence.entity.Card;
import vr.authorizer.infrastructure.persistence.entity.CardBalance;

import java.math.BigDecimal;

@Repository
public interface CardBalanceRepository extends JpaRepository<CardBalance, Long> {

    @Query("select sum(amount) from CardBalance where card = :card and type = 'DEBIT'")
    BigDecimal sumDebitAmountByCard(Card card);

    @Query("select sum(amount) from CardBalance where card = :card and type = 'CREDIT'")
    BigDecimal sumCreditAmountByCard(Card card);

}
