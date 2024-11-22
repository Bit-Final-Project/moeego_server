package com.main.moeego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@ComponentScan(basePackages = {"com.main.moeego", "admin","member"})
@SpringBootApplication
//@EnableJpaRepositories(basePackages = {"admin.repository"})
@EntityScan(basePackages = {"article", "cancel", "category", "comment", "favorite", "image", "map", "member", "pro", "reserve"})
public class MoeegoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoeegoApplication.class, args);
    }

}
