package com.naveejr.accounts.service.client;

import com.naveejr.accounts.model.Cards;
import com.naveejr.accounts.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("cards")
public interface CardsFeignClient {

	@PostMapping(value = "myCards")
	List<Cards> getCardDetails(@RequestBody Customer customer);
}
