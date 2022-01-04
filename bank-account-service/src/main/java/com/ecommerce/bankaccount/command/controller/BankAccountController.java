package com.ecommerce.bankaccount.command.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.bankaccount.command.dto.CreateAccountRequest;
import com.ecommerce.bankaccount.command.dto.DepositRequest;
import com.ecommerce.bankaccount.command.dto.WithdrawalRequest;
import com.ecommerce.bankaccount.command.service.AccountCommandService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("銀行帳戶API")
@RestController
@RequestMapping(value = "/bank-account")
public class BankAccountController {

	private final AccountCommandService accountCommandService;

	public BankAccountController(AccountCommandService accountCommandService) {
		this.accountCommandService = accountCommandService;
	}

	@ApiOperation("新增一個銀行帳戶")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "body", dataType = "CreateAccountRequest", name = "request", value = "新增帳號請求", required = true) })
	@ApiResponses({ @ApiResponse(code = 500, message = "系統錯誤") })
	@PostMapping(value = "/create")
	public ResponseEntity<String> createAccount(@RequestBody CreateAccountRequest request) {
		try {
			CompletableFuture<String> response = accountCommandService.createAccount(request);

			return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation("存款")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "body", dataType = "DepositRequest", name = "request", value = "存款資訊", required = true) })
	@ApiResponses({ @ApiResponse(code = 500, message = "系統錯誤") })
	@PutMapping(value = "/deposit")
	public ResponseEntity<String> deposit(@RequestBody DepositRequest request) {
		try {
			accountCommandService.depositToAccount(request);

			return new ResponseEntity<>("Amount credited", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation("扣款")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "body", dataType = "WithdrawalRequest", name = "request", value = "扣款資訊", required = true) })
	@ApiResponses({ @ApiResponse(code = 500, message = "系統錯誤") })
	@PutMapping(value = "/withdraw")
	public ResponseEntity<String> withdraw(@RequestBody WithdrawalRequest request) {
		try {
			accountCommandService.withdrawFromAccount(request);

			return new ResponseEntity<>("Amount debited.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
