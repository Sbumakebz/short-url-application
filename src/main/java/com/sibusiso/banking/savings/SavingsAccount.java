package com.sibusiso.banking.savings;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="savings_account")
@Component
public class SavingsAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;
    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "amount")
    private Double amount;

    public SavingsAccount() {
    }

    public SavingsAccount(String accountNumber, Double amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object otherAccount) {
        if(this.getAccountNumber().equals(((SavingsAccount) otherAccount).getAccountNumber()))
            return true;
        return false;
    }
}
