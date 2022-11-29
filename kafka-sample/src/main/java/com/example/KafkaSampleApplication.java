package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ${USER}
 * @version 1.0.0
 * @since ${YEAR}/${MONTH}/${DAY} ${HOUR}:${MINUTE}
 */
@SpringBootApplication
public class KafkaSampleApplication {
  public static void main(String[] args) {
    SpringApplication.run(KafkaSampleApplication.class);
    System.out.println("Hello world!");

  }
}