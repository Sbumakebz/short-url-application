package com.sibusiso.banking.account.savings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/savings")
public class SavingsAccountServiceController {
    @Autowired
    AccountService service;

    @Autowired
    AccountRepository repository;

    @GetMapping(value = "/create/{account}/{amount}")
    public String create(@PathVariable("account") String account, @PathVariable("amount") Double amount) throws BankingException {
        return service.createAccount(account, amount);
    }

    @PutMapping(value = "/deposit/{account}/{amount}")
    public String deposit(@PathVariable("account") String account, @PathVariable("amount") Double amount) throws BankingException {
        return service.depositAccount(account, amount);
    }

    @PutMapping(value = "/draw/{account}/{amount}")
    public String draw(@PathVariable("account") String account, @PathVariable("amount") Double amount) throws BankingException {
        return service.drawAccount(account, amount);
    }
}
