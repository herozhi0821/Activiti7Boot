package com.example.activiti7boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.example.activiti7boot.**.dao")
@SpringBootApplication
public class Activiti7BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Activiti7BootApplication.class, args);
    }

}
