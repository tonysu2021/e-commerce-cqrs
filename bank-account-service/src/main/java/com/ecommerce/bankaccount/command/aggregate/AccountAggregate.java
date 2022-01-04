package com.ecommerce.bankaccount.command.aggregate;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.ecommerce.bankaccount.command.command.CreateAccountCommand;
import com.ecommerce.bankaccount.command.command.DepositMoneyCommand;
import com.ecommerce.bankaccount.command.command.WithdrawMoneyCommand;
import com.ecommerce.bankaccount.common.event.AccountActivatedEvent;
import com.ecommerce.bankaccount.common.event.AccountCreatedEvent;
import com.ecommerce.bankaccount.common.event.AccountCreditedEvent;
import com.ecommerce.bankaccount.common.event.AccountDebitedEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class AccountAggregate {

	@AggregateIdentifier
	private String accountId;
	private BigDecimal balance;
	private AccountStatus status;

	public AccountAggregate() {
	}

	/**
	 * 創建帳號命令
	 * 
	 */
	@CommandHandler
	public AccountAggregate(CreateAccountCommand command) {
		log.info("[Account][Command] CreateAccountCommand received.");
		AggregateLifecycle.apply(new AccountCreatedEvent(command.getId(), command.getBalance()));
	}

	/**
	 * 帳戶創建事件
	 * 
	 */
	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		log.info("[Account][Command] An AccountCreatedEvent occurred. ");
		this.accountId = event.getId();
		this.balance = event.getBalance();
		this.status = AccountStatus.CREATED;

		AggregateLifecycle.apply(new AccountActivatedEvent(this.accountId, AccountStatus.ACTIVATED.name()));
	}

	/**
	 * 帳戶已激活事件
	 * 
	 */
	@EventSourcingHandler
	public void on(AccountActivatedEvent event) {
		log.info("[Account][Command] An AccountActivatedEvent occurred. ");
		this.status = AccountStatus.valueOf(event.getStatus());
	}

	/**
	 * 存款命令
	 * 
	 */
	@CommandHandler
	public void on(DepositMoneyCommand command) {
		log.info("[Account][Command] DepositMoneyCommand received.");
		AggregateLifecycle.apply(new AccountCreditedEvent(command.getId(), command.getAmount()));
	}

	/**
	 * 入帳事件
	 * 
	 */
	@EventSourcingHandler
	public void on(AccountCreditedEvent event) {
		log.info("[Account][Command] An AccountCreditedEvent occurred. ");
		this.balance = this.balance.add(event.getAmount());
	}

	/**
	 * 扣款命令
	 * 
	 */
	@CommandHandler
	public void on(WithdrawMoneyCommand command) {
		log.info("[Account][Command] WithdrawMoneyCommand received.");
		AggregateLifecycle.apply(new AccountDebitedEvent(command.getId(), command.getAmount()));
	}

	/**
	 * 借款事件
	 * 
	 */
	@EventSourcingHandler
	public void on(AccountDebitedEvent event) {
		log.info("[Account][Command] An AccountDebitedEvent occurred. ");
		this.balance = this.balance.subtract(event.getAmount());
	}
}
