package com.example.lab09.service;

import com.example.lab09.model.Account;
import com.example.lab09.repository.AccountRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void add(Account account) {
        accountRepository.save(account);
    }

    public Optional<Account> findByIdAccount(Long id) {
        return accountRepository.findById(id);
    }
}
