package com.hybridpay;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Data
@SpringBootApplication
public class HybridPayApplication {
    public static void main(String[] args) {

        SpringApplication.run(HybridPayApplication.class, args);
    }
}
