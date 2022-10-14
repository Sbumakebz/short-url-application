package com.sibusiso.banking.savings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends JpaRepository<SavingsAccountTransaction, Integer> {
}
