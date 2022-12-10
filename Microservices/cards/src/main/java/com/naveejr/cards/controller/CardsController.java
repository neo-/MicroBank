package com.naveejr.cards.controller;


import com.naveejr.cards.config.CardsServiceConfig;
import com.naveejr.cards.model.Cards;
import com.naveejr.cards.model.Customer;
import com.naveejr.cards.model.Properties;
import com.naveejr.cards.repository.CardsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CardsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardsController.class);
    private final CardsServiceConfig cardsServiceConfig;

    private final CardsRepository cardsRepository;

    @PostMapping("/myCards")
    public List<Cards> getCardDetails(@RequestBody Customer customer) {
        LOGGER.info("Getting cards details for Customer {}", customer.getCustomerId());
        return cardsRepository.findByCustomerId(customer.getCustomerId());
    }

    @GetMapping("cards/properties")
    public ResponseEntity<Properties> getAccountServiceConfig() {
        return ResponseEntity.ok(
                new Properties(
                        cardsServiceConfig.getMsg(),
                        cardsServiceConfig.getBuildVersion(),
                        cardsServiceConfig.getMailDetails(),
                        cardsServiceConfig.getActiveBranches())
        );
    }

}
