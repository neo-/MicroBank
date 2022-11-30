/**
 *
 */
package com.naveejr.accounts.controller;

import com.naveejr.accounts.config.AccountsServiceConfig;
import com.naveejr.accounts.model.Accounts;
import com.naveejr.accounts.model.Customer;
import com.naveejr.accounts.model.Properties;
import com.naveejr.accounts.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class AccountsController {

	private final AccountsServiceConfig accountsServiceConfig;
	private final AccountsRepository accountsRepository;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		return accountsRepository.findByCustomerId(customer.getCustomerId());
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
