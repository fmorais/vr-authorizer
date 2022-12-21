package vr.authorizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vr.authorizer.domain.createcard.CreateCardService;
import vr.authorizer.domain.createcard.model.CardData;
import vr.authorizer.domain.transaction.TransactionService;

import java.math.RoundingMode;

@RestController
@RequiredArgsConstructor
public class AuthorizerController {

    private final CreateCardService createCardService;
    private final TransactionService transactionService;

    @PostMapping("/cartoes")
    public ResponseEntity<CardData> cards(@RequestBody CardData body) throws Exception {
        createCardService.process(body);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/cartoes/{numeroCartao}")
    public ResponseEntity<String> cardBalance(@PathVariable(name = "numeroCartao") String cardNumber) {
        var balance = transactionService.obtainBalance(cardNumber);
        return ResponseEntity.ok(balance.setScale(2, RoundingMode.UNNECESSARY).toString());
    }
}
