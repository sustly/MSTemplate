package com.sustly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author admin
 */
@SpringBootApplication
@ImportResource("classpath:spring.xml")
public class MsTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTemplateApplication.class, args);
    }

}