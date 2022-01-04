package com.ecommerce.bankaccount.query.service;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import com.ecommerce.bankaccount.common.event.AccountActivatedEvent;
import com.ecommerce.bankaccount.common.event.AccountCreatedEvent;
import com.ecommerce.bankaccount.common.event.AccountCreditedEvent;
import com.ecommerce.bankaccount.common.event.AccountDebitedEvent;
import com.ecommerce.bankaccount.query.entity.AccountEntity;
import com.ecommerce.bankaccount.query.query.FindAccountByIdQuery;
import com.ecommerce.bankaccount.query.repository.AccountRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManageAccountService {

	private final AccountRepository accountRepository;

	public ManageAccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@EventHandler
	public void on(AccountCreatedEvent event) {
		log.info("[Account][Query] Handling AccountCreatedEvent...");
		AccountEntity account = new AccountEntity();
		account.setAccountId(event.getId());
		account.setBalance(event.getBalance());
		account.setStatus("CREATED");
		accountRepository.save(account);
	}

	@EventHandler
	public void on(AccountActivatedEvent event) {
		log.info("[Account][Query] Handling AccountActivatedEvent...");
		String accountId = event.getId();
		AccountEntity account = accountRepository.findById(accountId).orElse(null);
		if (account == null) {
			return;
		}
		account.setStatus(event.getStatus());
		accountRepository.save(account);
	}

	@EventHandler
	public void on(AccountCreditedEvent event) {
		log.info("[Account][Query] Handling AccountCreditedEvent...");
		String accountId = event.getId();
		AccountEntity account = accountRepository.findById(accountId).orElse(null);
		if (account == null) {
			return;
		}
		account.setBalance(account.getBalance().add(event.getAmount()));
		accountRepository.save(account);
	}

	@EventHandler
	public void on(AccountDebitedEvent event) {
		log.info("[Account][Query] Handling AccountDebitedEvent...");
		String accountId = event.getId();
		AccountEntity account = accountRepository.findById(accountId).orElse(null);
		if (account == null) {
			return;
		}
		account.setBalance(account.getBalance().subtract(event.getAmount()));
		accountRepository.save(account);
	}

	@QueryHandler
	public AccountEntity handle(FindAccountByIdQuery query) {
		log.info("[Account][Query] Handling FindAccountByIdQuery...");
		return accountRepository.findById(query.getAccountId()).orElse(null);
	}
}
