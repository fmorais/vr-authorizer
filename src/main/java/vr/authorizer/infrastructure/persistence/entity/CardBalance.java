package vr.authorizer.infrastructure.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@SequenceGenerator(name = "card_balance_seq", allocationSize = 1)
@Getter
@Setter
public class CardBalance {

    @Id
    @GeneratedValue(generator = "card_balance_seq")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private TransactionType type;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public CardBalance() {
    }

    public CardBalance(TransactionType type, BigDecimal amount, Card card) {
        this.type = type;
        this.amount = amount;
        this.card = card;
    }

    public static CardBalance debit(Card card, BigDecimal amount) {
        return new CardBalance(TransactionType.DEBIT, amount, card);
    }

    public static CardBalance credit(Card card, BigDecimal amount) {
        return new CardBalance(TransactionType.CREDIT, amount, card);
    }

    public enum TransactionType {
        DEBIT, CREDIT
    }
}
