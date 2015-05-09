package com.mmrath.sapp.config;


import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

  @Bean
  public JSR310Module jacksonJodaModule() {
    JSR310Module module = new JSR310Module();
    return module;
  }
}
