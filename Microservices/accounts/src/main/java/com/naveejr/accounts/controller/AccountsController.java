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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class AccountsController {

	private final AccountsServiceConfig accountsServiceConfig;
	private final AccountsRepository accountsRepository;

	private final LoansFeignClient loansFeignClient;

	private final CardsFeignClient cardsFeignClient;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		return accountsRepository.findByCustomerId(customer.getCustomerId());
	}

	@PostMapping("/myCustomerDetails")
	@CircuitBreaker(name = "detailsForCustomerSupportApp")
	public CustomerDetails getCustomerDetails(@RequestBody Customer customer) {
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
		return ResponseEntity.ok(
				new Properties(
						accountsServiceConfig.getMsg(),
						accountsServiceConfig.getBuildVersion(),
						accountsServiceConfig.getMailDetails(),
						accountsServiceConfig.getActiveBranches())
		);
	}

}
