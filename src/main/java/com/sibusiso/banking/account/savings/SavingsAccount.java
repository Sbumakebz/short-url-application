package com.sibusiso.banking.account.savings;


import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="savings_account")
@Component
public class SavingsAccount implements Serializable {
    private String accountNumber;
    private Double amount;

    public SavingsAccount() {
    }

    public SavingsAccount(String accountNumber, Double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
    @Column(name = "account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
