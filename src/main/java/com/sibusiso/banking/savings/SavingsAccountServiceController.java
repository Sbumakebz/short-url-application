package com.sibusiso.banking.savings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/savings")
public class SavingsAccountServiceController {
    @Autowired
    AccountService service;

    @Autowired
    AccountRepository repository;

    @GetMapping(value = "/create/{account}/{amount}")
    public String create(@PathVariable("account") String account, @PathVariable("amount") Double amount)
            throws ResponseStatusException {
        return service.createAccount(account, amount);
    }

    @GetMapping(value = "/deposit/{account}/{amount}")
    public String deposit(@PathVariable("account") String account, @PathVariable("amount") Double amount)
            throws BankingException, ResponseStatusException {
        return service.depositAccount(account, amount);
    }

    @GetMapping(value = "/draw/{account}/{amount}")
    public String draw(@PathVariable("account") String account, @PathVariable("amount") Double amount)
            throws BankingException, ResponseStatusException {
        return service.drawAccount(account, amount);
    }

    @GetMapping(value = "/transfer/{fromAccountNumber}/{amount}/{toAccountNumber}")
    public String transfer(@PathVariable("fromAccountNumber") String fromAccountNumber, @PathVariable("amount") Double amount,
                           @PathVariable("toAccountNumber") String toAccountNumber)
            throws BankingException, ResponseStatusException {
        return service.transferFromAccount(fromAccountNumber, amount, toAccountNumber);
    }
}
