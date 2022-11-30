package com.naveejr.loans.controller;

import com.naveejr.loans.config.LoanServiceConfig;
import com.naveejr.loans.model.Customer;
import com.naveejr.loans.model.Loans;
import com.naveejr.loans.model.Properties;
import com.naveejr.loans.repository.LoansRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class LoansController {

	private final LoanServiceConfig loanServiceConfig;
	private final LoansRepository loansRepository;

	@PostMapping("/myLoans")
	public List<Loans> getLoansDetails(@RequestBody Customer customer) {
		List<Loans> loans = loansRepository.findByCustomerIdOrderByStartDtDesc(customer.getCustomerId());
		if (loans != null) {
			return loans;
		} else {
			return null;
		}

	}



	@GetMapping("loans/properties")
	public ResponseEntity<Properties> getAccountServiceConfig() {
		return ResponseEntity.ok(
				new Properties(
						loanServiceConfig.getMsg(),
						loanServiceConfig.getBuildVersion(),
						loanServiceConfig.getMailDetails(),
						loanServiceConfig.getActiveBranches())
		);
	}

}
