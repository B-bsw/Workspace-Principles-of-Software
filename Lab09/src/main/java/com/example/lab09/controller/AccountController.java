package com.example.lab09.controller;

import com.example.lab09.model.Account;
import com.example.lab09.service.AccountService;
import com.example.lab09.service.DepositService;
import java.util.Collections;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final DepositService depositService;

    public AccountController(
        AccountService accountService,
        DepositService depositService
    ) {
        this.accountService = accountService;
        this.depositService = depositService;
    }

    @PostMapping("")
    public ResponseEntity<Account> AddAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.add(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> GetById(@PathVariable Long id) {
        return ResponseEntity.ok(
            accountService.findByIdAccount(id).orElseThrow()
        );
    }

    @PostMapping("{id}/deposit")
    public ResponseEntity<Map<String, String>> deposit(
        @PathVariable("id") Long accountId,
        @RequestBody Map<String, Double> reqAmount
    ) {
        depositService.deposit(accountId, reqAmount.get("amount"));

        return ResponseEntity.ok(
            Collections.singletonMap("message", "Deposit successful")
        );
    }
}
