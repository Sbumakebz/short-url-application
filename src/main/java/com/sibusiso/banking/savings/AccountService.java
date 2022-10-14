package com.sibusiso.banking.savings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.sibusiso.banking.account.savings"})
@EnableTransactionManagement
@EntityScan(basePackages="com.sibusiso.banking.account.savings")
//@Transactional
public class AccountService {
    Logger logger = LoggerFactory.getLogger(AccountService.class);
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountTransactionRepository transactionRepository;

    //@Transactional
    public String createAccount(String accountNumber, Double amount) throws ResponseStatusException {
        logger.debug("To create a savings account: " + accountNumber + " with initial amount " + amount);
        if(amount.longValue() < 0) {
            logger.error("Invalid amount: " + amount);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount: " + amount);
        }

        try {
            Long.parseLong(accountNumber);
        } catch (NumberFormatException nfe) {
            logger.error("Invalid Account Number : " + accountNumber);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Account Number : " + accountNumber);
        }

        SavingsAccount account = new SavingsAccount(accountNumber, amount);
        SavingsAccountTransaction accountTransaction = new SavingsAccountTransaction(accountNumber,
                amount, "create", new Date());
        accountRepository.save(account);
        transactionRepository.save(accountTransaction);
        return "Savings Account " + accountNumber + " Created with amount: " + amount;
    }

    //@Transactional
    public synchronized String depositAccount(String accountNumber, Double amount) throws BankingException,
            ResponseStatusException {
        logger.debug("To deposit into account: " + accountNumber + " with amount " + amount);
        if(amount.longValue() < 0) {
            logger.error("Invalid amount deposited.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount deposited.");
        }

        List<SavingsAccount> accounts = accountRepository.getAccount(accountNumber);
        if(accounts == null || accounts.isEmpty()) {
            logger.error("Account Number not found.");
            throw new BankingException("Account Number not found.");
        }

        int updateCount = 0;
        for(SavingsAccount account : accounts) {
            updateCount = accountRepository.updateAccount(accountNumber, (account.getAmount() + amount));
            if(updateCount > 0) {
                SavingsAccountTransaction accountTransaction = new SavingsAccountTransaction(accountNumber,
                        amount, "deposit", new Date());
                transactionRepository.save(accountTransaction);
            }
        }

        return updateCount > 0 ? "Amount " + amount + " deposited into Account " + accountNumber + "." :
               "Deposit Failed into Account: " + accountNumber;
    }

    //@Transactional
    public synchronized String drawAccount(String accountNumber, Double amount) throws BankingException, ResponseStatusException {
        logger.debug("To withdraw from account: " + accountNumber + " amount " + amount);
        if(amount.longValue() < 0) {
            logger.error("Invalid amount drawn.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount drawn.");
        }

        List<SavingsAccount> accounts = accountRepository.getAccount(accountNumber);
        if(accounts == null || accounts.isEmpty()) {
            logger.error("Account Number not found.");
            throw new BankingException("Account Number not found.");
        }

        int updateCount = 0;
        for(SavingsAccount account : accounts) {
            updateCount = accountRepository.updateAccount(accountNumber, (account.getAmount() + amount));
            if(updateCount > 0) {
                SavingsAccountTransaction accountTransaction = new SavingsAccountTransaction(accountNumber,
                        amount, "draw", new Date());
                transactionRepository.save(accountTransaction);
            }
        }

        return updateCount > 0 ? "Amount " + amount + " withdrawn from Account " + accountNumber + "." :
                "Withdrawal Failed from Account: " + accountNumber;
    }

    //@Transactional
    public synchronized String transferFromAccount(String fromAccountNumber, Double amount, String toAccountNumber)
            throws BankingException, ResponseStatusException{
            //send transfer request to TRANSFERS application(a eureka client)
            //if transfer was successful return string message

            return "Transfer from account: " + fromAccountNumber + " to account: " + toAccountNumber + "was successful.";
    }
}

