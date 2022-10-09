package com.sibusiso.banking.account.savings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableEurekaClient
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.sibusiso.banking.account.savings"})
@EnableJpaRepositories(basePackages="com.sibusiso.banking.account.savings")
@EnableTransactionManagement
@EntityScan(basePackages="com.sibusiso.banking.account.savings")
public class SavingsAccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SavingsAccountServiceApplication.class, args);
	}

}
