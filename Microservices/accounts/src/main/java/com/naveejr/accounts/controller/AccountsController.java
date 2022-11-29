/**
 *
 */
package com.naveejr.accounts.controller;

import com.naveejr.accounts.model.Accounts;
import com.naveejr.accounts.model.Customer;
import com.naveejr.accounts.repository.AccountsRepository;
import com.naveejr.common.util.StopWatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountsController {

	private final AccountsRepository accountsRepository;

	@PostMapping("/myAccount")
	public Accounts getAccountDetails(@RequestBody Customer customer) {
		System.out.println(StopWatch.getName());
		Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
		if (accounts != null) {
			return accounts;
		} else {
			return null;
		}

	}

}
