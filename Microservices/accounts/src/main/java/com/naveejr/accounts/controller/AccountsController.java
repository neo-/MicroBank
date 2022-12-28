/**
 *
 */
package com.naveejr.accounts.controller;

import com.naveejr.accounts.config.AccountsServiceConfig;
import com.naveejr.accounts.model.*;
import com.naveejr.accounts.repository.AccountsRepository;
import com.naveejr.accounts.service.client.CardsFeignClient;
import com.naveejr.accounts.service.client.LoansFeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.core.annotation.Timed;
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
public class AccountsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);

	private final AccountsServiceConfig accountsServiceConfig;
	private final AccountsRepository accountsRepository;

	private final LoansFeignClient loansFeignClient;

	private final CardsFeignClient cardsFeignClient;

	@PostMapping("/myAccount")
	@Timed(value = "getAccountDetails.time", description = "Time taken to return Account Details")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		return accountsRepository.findByCustomerId(customer.getCustomerId());
	}

	@PostMapping("/myCustomerDetails")
	@CircuitBreaker(name = "detailsForCustomerSupportApp")
	public CustomerDetails getCustomerDetails(@RequestBody Customer customer) {
		LOGGER.info("Getting customer details for {}", customer.getCustomerId());
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		List<Loans> loans = loansFeignClient.getLoansDetails(customer);
		List<Cards> cards = cardsFeignClient.getCardDetails(customer);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setAccounts(accounts);
		customerDetails.setLoans(loans);
		customerDetails.setCards(cards);
		return customerDetails;
	}

	@GetMapping("account/properties")
	public ResponseEntity<Properties> getAccountServiceConfig() {

		LOGGER.info("Getting customer details ");
		return ResponseEntity.ok(
				new Properties(
						accountsServiceConfig.getMsg(),
						accountsServiceConfig.getBuildVersion(),
						accountsServiceConfig.getMailDetails(),
						accountsServiceConfig.getActiveBranches())
		);
	}

	@GetMapping("hello")
	public ResponseEntity<String> helloWorld() {
		return ResponseEntity.ok("Hello world!");
	}

}
