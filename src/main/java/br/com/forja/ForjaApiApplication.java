package br.com.forja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.forja")
@EntityScan(basePackages = "br.com.forja")
public class ForjaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                ForjaApiApplication.class,
                args);

        // System.out.println("POSTGRES_USER=" + System.getenv("POSTGRES_USER"));
        // System.out.println("POSTGRES_PASSWORD=" +
        // System.getenv("POSTGRES_PASSWORD"));
        // System.out.println("SPRING_DATASOURCE_URL=" +
        // System.getenv("SPRING_DATASOURCE_URL"));

    }

    // @Bean
    // CommandLineRunner debugEnv(
    // @Value("${POSTGRES_USER}") String user,
    // @Value("${POSTGRES_PASSWORD}") String password,
    // @Value("${SPRING_DATASOURCE_URL}") String url) {
    // return args -> {
    // System.out.println("=====================================");
    // System.out.println("URL = " + url);
    // System.out.println("USER = " + user);
    // System.out.println("PASSWORD = " + password);
    // System.out.println("=====================================");
    // };
    // }
}