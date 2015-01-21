package com.mmrath.sapp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by murali on 19/01/15.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class TestConfiguration {

}
