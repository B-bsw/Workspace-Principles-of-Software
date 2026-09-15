package com.example.lab09.repository;

import com.example.lab09.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<DepositTransaction, Long> {
}
