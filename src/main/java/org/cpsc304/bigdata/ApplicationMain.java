package org.cpsc304.bigdata;

import org.cpsc304.bigdata.controller.DatabaseConnectionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class ApplicationMain {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }

    @Bean
    public DatabaseConnectionHandler databaseConnectionHandler() {
        return new DatabaseConnectionHandler(
                System.getenv("USERNAME"),
                System.getenv("PASSWORD")
        );
    }
}
