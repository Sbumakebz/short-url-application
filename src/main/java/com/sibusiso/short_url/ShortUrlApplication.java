package com.sibusiso.short_url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.sibusiso.short_url"})
@EnableJpaRepositories(basePackages="com.sibusiso.short_url")
@EnableTransactionManagement
@EntityScan(basePackages="com.sibusiso.short_url")
public class ShortUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortUrlApplication.class, args);
	}
}
