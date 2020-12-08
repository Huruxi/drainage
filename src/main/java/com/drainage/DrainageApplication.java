package com.drainage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
//@MapperScan("com.drainage.mapper")
public class DrainageApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrainageApplication.class, args);
    }
}
