package ru.kogtev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TitanicTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(TitanicTestTaskApplication.class, args);
    }

}
