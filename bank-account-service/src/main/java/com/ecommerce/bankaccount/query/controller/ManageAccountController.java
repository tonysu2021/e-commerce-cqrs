package com.ecommerce.bankaccount.query.controller;

import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.bankaccount.query.entity.AccountEntity;
import com.ecommerce.bankaccount.query.query.FindAccountByIdQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("銀行帳戶管理API")
@RestController
@RequestMapping(value = "/manage-account")
public class ManageAccountController {

    private final QueryGateway queryGateway;

    public ManageAccountController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @ApiOperation("查詢銀行帳戶資訊")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", dataType = "String", name = "id", value = "銀行帳戶ID", required = true) })
    @GetMapping("/get-account")
    public ResponseEntity<AccountEntity> getAccount(@RequestParam String id) {
        AccountEntity account = queryGateway.query(
                new FindAccountByIdQuery(id), AccountEntity.class
        ).join();

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
