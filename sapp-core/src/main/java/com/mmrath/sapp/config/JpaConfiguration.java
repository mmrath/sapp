package com.mmrath.sapp.config;

import com.mmrath.sapp.domain.AbstractAuditingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories("com.mmrath.sapp.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class JpaConfiguration {

  @Autowired
  Environment environment;

  @Autowired
  DataSource dataSource;

  @Bean
  @DependsOn("liquibase")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactory =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setPackagesToScan(AbstractAuditingEntity.class.getPackage().getName());
    entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

    entityManagerFactory.setDataSource(dataSource);

    Map<String, String> jpaProperties = new HashMap<>();

    jpaProperties.put("hibernate.dialect", getProperty("dialect"));
    jpaProperties.put("hibernate.show_sql", getProperty("show_sql", "false"));
    jpaProperties.put("hibernate.hbm2ddl.auto", getProperty("hbm2ddl.auto", "none"));

    jpaProperties.put("hibernate.cache.use_second_level_cache",
        getProperty("cache.use_second_level_cache", "false"));

    entityManagerFactory.setJpaPropertyMap(jpaProperties);
    return entityManagerFactory;
  }


  @Bean
  public PlatformTransactionManager transactionManager() {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
    return transactionManager;
  }

  private String getProperty(String property){
    return environment.getProperty("hibernate."+property);
  }
  private String getProperty(String property, String defaultValue){
    return environment.getProperty("hibernate."+property, defaultValue);
  }


}
