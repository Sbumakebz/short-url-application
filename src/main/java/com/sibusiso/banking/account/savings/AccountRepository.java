package com.sibusiso.banking.account.savings;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public class AccountRepository {
    @Modifying
    @Query(value = "INSERT INTO savings_account (account_number, amount) VALUES (:accountNumber,:amount)", nativeQuery = true)
    public void createAccount(@Param("accountNumber") String accountNumber, @Param("amount") Double amount) {

    }


    @Modifying
    @Query(value = "SELECT account FROM savings_account account WHERE account.account_number=?#{:accountNumber}", nativeQuery = true)
    public SavingsAccount getAccount(@Param("accountNumber") String accountNumber) {
        return null;
    }

    @Modifying
    @Query(value = "UPDATE savings_account SET amount=?#{amount} WHERE account_number=?#{:accountNumber}", nativeQuery = true)
    public void updateAccount(@Param("accountNumber") String accountNumber, @Param("amount") Double amount) {

    }
}
