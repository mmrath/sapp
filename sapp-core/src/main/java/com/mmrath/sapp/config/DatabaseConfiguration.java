package com.mmrath.sapp.config;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.mmrath.sapp.Constants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class DatabaseConfiguration implements EnvironmentAware {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private Environment env;

    @Autowired(required = false)
    private MetricRegistry metricRegistry;

    @Override
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    @Bean(destroyMethod = "shutdown")
    @Profile("!" + Constants.SPRING_PROFILE_CLOUD)
    public DataSource dataSource() {
        log.debug("Configuring Datasource");
        if (getProperty("url") == null && getProperty("databaseName") == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    "cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariConfig config = new HikariConfig();
        config.setDataSourceClassName(getProperty("dataSourceClassName"));
        if (getProperty("url") == null || "".equals(getProperty("url"))) {
            config.addDataSourceProperty("databaseName", getProperty("databaseName"));
            config.addDataSourceProperty("serverName", getProperty("serverName"));
        } else {
            config.addDataSourceProperty("url", getProperty("url"));
        }
        config.addDataSourceProperty("user", getProperty("username"));
        config.addDataSourceProperty("password", getProperty("password"));

        //MySQL optimizations, see https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
        if ("com.mysql.jdbc.jdbc2.optional.MysqlDataSource".equals(getProperty(
            "dataSourceClassName"))) {
            config.addDataSourceProperty("cachePrepStmts", getProperty("cachePrepStmts", "true"));
            config.addDataSourceProperty("prepStmtCacheSize", getProperty("prepStmtCacheSize",
                "250"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", getProperty(
                "prepStmtCacheSqlLimit", "2048"));
            config.addDataSourceProperty("useServerPrepStmts", getProperty("useServerPrepStmts",
                "true"));
        }
        if (metricRegistry != null) {
            config.setMetricRegistry(metricRegistry);
        }
        return new HikariDataSource(config);
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/changelog/changelog-master.xml");
        liquibase.setContexts("development, production");
        if (env.acceptsProfiles(Constants.SPRING_PROFILE_FAST)) {
            liquibase.setShouldRun(false);
        } else {
            log.debug("Configuring Liquibase");
        }
        return liquibase;
    }

    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }
    
    
    private String getProperty(String property){
        return env.getProperty("database."+property);
    }
    private String getProperty(String property, String defaultValue){
        return env.getProperty("database."+property, defaultValue);
    }
}
