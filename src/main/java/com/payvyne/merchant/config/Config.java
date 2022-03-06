package com.payvyne.merchant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

@Configuration
public class Config {

  public @Bean IdGenerator uuidGenerator() {
    return new JdkIdGenerator();
  }
}
