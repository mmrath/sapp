package com.mmrath.sapp.config;

import com.mmrath.sapp.Constants;
import com.mmrath.sapp.domain.AbstractAuditingEntity;
import com.mmrath.sapp.repository.PersistenceAuditEventRepository;
import com.mmrath.sapp.repository.security.PersistentTokenRepository;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import java.util.Arrays;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
    basePackageClasses = {
        PersistenceAuditEventRepository.class,
        PersistentTokenRepository.class
    }
)
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
@EntityScan(basePackageClasses = {AbstractAuditingEntity.class,Jsr310JpaConverters.class})
public class DatabaseConfiguration{

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }

}
