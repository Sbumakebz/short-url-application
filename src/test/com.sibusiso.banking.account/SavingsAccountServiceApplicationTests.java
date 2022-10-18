package com.sibusiso.banking.account.savings;

import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SavingsAccountServiceController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SavingsAccountServiceApplicationTests {

    @MockBean
    private EurekaClient eurekaClient;
    private String accountNumber;
    private String url;
    private WebClient webClient;
    private WebClient.ResponseSpec responseSpec;

    @BeforeAll
    public void setAccountNumber() {
        accountNumber  = String.valueOf((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L);
        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("SAVINGS-ACCOUNT-CLIENT", false);
        String savingServiceHomeUrl = "http://" + instanceInfo.getAppName() + ":" + instanceInfo.getPort();
        System.out.println(savingServiceHomeUrl);
        webClient = WebClient.create(savingServiceHomeUrl);
    }

    @Test
    @Order(1)
    void testCreateAccount() {
        String expectedMessage = "Savings Account " + accountNumber
                + " Created with amount: " + 1234;
        String message = webClient.get().uri("/create/{account}/{amount}", accountNumber, 1234)
                .retrieve().bodyToMono(String.class).block();
        Assert.isTrue(message.equals(expectedMessage), "Failed Account creation test.");

        //test validity of account number
        Exception exception = assertThrows(BankingException.class, () -> {
            webClient.get().uri("/create/{account}/{amount}", "652jeb9ui8", 1234)
                    .retrieve().bodyToMono(String.class).block();
        });

        expectedMessage = "Invalid Account Number : 652jeb9ui8";
        String actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Failed Invalid Account Number test.");

        //test validity of amount
        exception = assertThrows(BankingException.class, () -> {
            webClient.get().uri("/create/{account}/{amount}", accountNumber, -1234)
                    .retrieve().bodyToMono(String.class).block();
        });

        expectedMessage = "Invalid amount: -1234";
        actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Failed Invalid Amount test.");
    }

    @Test
    @Order(2)
    void testDepositAccount() {
        String expectedMessage = "Amount " + 1234 + " deposited into Account " + accountNumber + ".";
        String message = webClient.get().uri("/deposit/{account}/{amount}", accountNumber, 1234)
                .retrieve().bodyToMono(String.class).block();
        Assert.isTrue(message.equals(expectedMessage), "Failed Account deposit test.");

        Exception exception = assertThrows(BankingException.class, () -> {
            webClient.get().uri("/deposit/{account}/{amount}", accountNumber, 1234)
                    .retrieve().bodyToMono(String.class).block();
        });

        //test amount validity
        expectedMessage = "Invalid amount deposited.";
        String actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Failed Invalid deposit Amount test.");

        //test account if it exists
        exception = assertThrows(BankingException.class, () -> {
            webClient.get().uri("/deposit/{account}/{amount}", accountNumber, -1234)
                    .retrieve().bodyToMono(String.class).block();
        });

        expectedMessage = "Account Number not found.";
        actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Failed Account Number test.");
    }

    @Test
    @Order(3)
    void testDrawAccount() {
        String expectedMessage = "Amount " + 1234 + " withdrawn from Account " + accountNumber + ".";
        String message = webClient.get().uri("/draw/{account}/{amount}", accountNumber, 1234)
                .retrieve().bodyToMono(String.class).block();
        Assert.isTrue(message.equals(expectedMessage), "Failed Account draw test.");

        Exception exception = assertThrows(BankingException.class, () -> {
            webClient.get().uri("/draw/{account}/{amount}", accountNumber, 1234)
                    .retrieve().bodyToMono(String.class).block();
        });

        //test amount validity
        expectedMessage = "Invalid amount drawn.";
        String actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Failed Invalid deposit Amount test.");

        //test account if it exists
        exception = assertThrows(BankingException.class, () -> {
            webClient.get().uri("/deposit/{account}/{amount}", accountNumber, -1234)
                    .retrieve().bodyToMono(String.class).block();
        });

        expectedMessage = "Account Number not found.";
        actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Failed Account Number test.");

    }
}
