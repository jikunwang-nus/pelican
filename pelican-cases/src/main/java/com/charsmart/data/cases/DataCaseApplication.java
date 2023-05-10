package com.charsmart.data.cases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Wonder
 * @Date: Created on 2022/5/13 6:17 PM
 */
@SpringBootApplication(scanBasePackages = "com.charsmart.data")
public class DataCaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataCaseApplication.class, args);
    }
}
