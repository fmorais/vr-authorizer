package vr.authorizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vr.authorizer.domain.createcard.CreateCardService;
import vr.authorizer.domain.createcard.model.CardData;

@RestController
@RequiredArgsConstructor
public class AuthorizerController {

    private final CreateCardService createCardService;

    @PostMapping("/cartoes")
    public ResponseEntity<CardData> cards(@RequestBody CardData body) throws Exception {
        return ResponseEntity.ok(createCardService.process(body));
    }
}
