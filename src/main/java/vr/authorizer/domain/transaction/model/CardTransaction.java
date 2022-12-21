package vr.authorizer.domain.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardTransaction {

    @JsonProperty(value = "numeroCartao")
    private String cardNumber;

    @JsonProperty(value = "senhaCartao")
    private String password;

    @JsonProperty(value = "valor")
    private BigDecimal amount;
}
