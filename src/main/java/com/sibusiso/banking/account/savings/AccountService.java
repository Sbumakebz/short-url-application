package com.sibusiso.banking.account.savings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {
    Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    AccountRepository repository;
    private SavingsAccount account;

    @Transactional
    public String createAccount(String accountNumber, Double amount) throws BankingException {
        logger.debug("To create a savings account: " + accountNumber + " with initial amount + " + amount);
        if(amount.longValue() < 0) {
            logger.error("Invalid amount: " + amount);
            throw new BankingException("Invalid amount: " + amount);
        }

        try {
            Long.parseLong(accountNumber);
        } catch (NumberFormatException nfe) {
            logger.error("Invalid Account Number : " + accountNumber);
            throw new BankingException("Invalid Account Number : " + accountNumber);
        }
        repository.createAccount(accountNumber, amount);
        return "Savings Account " + accountNumber + " Created with amount: " + amount;
    }

    @Transactional
    public synchronized String depositAccount(String accountNumber, Double amount) throws BankingException {
        logger.debug("To deposit into account: " + accountNumber + " with amount " + amount);
        if(amount.longValue() < 0) {
            logger.error("Invalid amount deposited.");
            throw new BankingException("Invalid amount deposited.");
        }

        account = repository.getAccount(accountNumber);
        if(account == null) {
            logger.error("Account Number not found.");
            throw new BankingException("Account Number not found.");
        }

        repository.updateAccount(accountNumber, (account.getAmount() + amount));
        return "Amount " + amount + " deposited into Account " + accountNumber + ".";
    }

    @Transactional
    public synchronized String drawAccount(String accountNumber, Double amount) throws BankingException {
        logger.debug("To withdraw from account: " + accountNumber + " amount " + amount);
        if(amount.longValue() < 0) {
            logger.error("Invalid amount drawn.");
            throw new BankingException("Invalid amount drawn.");
        }

        account = repository.getAccount(accountNumber);
        if(account == null) {
            throw new BankingException("Account Number not found.");
        }

        repository.updateAccount(accountNumber, (account.getAmount() - amount));
        return "Amount " + amount + " withdrawn from Account " + accountNumber + ".";
    }
}

