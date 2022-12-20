package vr.authorizer.infrastructure.persistence.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@SequenceGenerator(name = "card_sequence")
@Getter
@Setter
@EqualsAndHashCode(of = "number")
public class Card {

    @Id
    @GeneratedValue(generator = "card_sequence")
    private Long id;

    @Column(unique = true)
    private String number;
    private String password;

    public Card() {
    }

    public Card(String number, String password) {
        this.number = number;
        this.password = password;
    }

    // Gosto de adotar o design pattern Factory Method para a criação e conversão de tipos e classes.
    public static Card fromNumberAndPassword(String number, String password) {
        return new Card(number, password);
    }
}
