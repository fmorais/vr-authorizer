package vr.authorizer.domain.createcard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CardData {

    @JsonProperty(value = "numeroCartao")
    private String number;
    @JsonProperty(value = "senha")
    private String password;

    public CardData() {
    }

    public CardData(String number, String password) {
        this.number = number;
        this.password = password;
    }

}
