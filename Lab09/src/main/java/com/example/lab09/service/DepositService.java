package com.example.lab09.service;

import com.example.lab09.model.Account;
import com.example.lab09.model.DepositTransaction;
import com.example.lab09.repository.AccountRepository;
import com.example.lab09.repository.DepositRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final AccountRepository accountRepository;

    public DepositService(
        DepositRepository depositRepository,
        AccountRepository accountRepository
    ) {
        this.depositRepository = depositRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void deposit(Long accountId, Double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        DepositTransaction depositTransaction = new DepositTransaction();

        // บันทึก acc && amount เข้า transaction เพื่อบันทึก
        depositTransaction.setAccount(account);
        depositTransaction.setAmount(amount);

        account.setBalance(account.getBalance() + amount); // บวกจากของเก่า

        accountRepository.save(account);
        depositRepository.save(depositTransaction);
    }
}
