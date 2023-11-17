package com.opl.cbdc.config.utils;

import com.opl.cbdc.utils.config.*;
import com.zaxxer.hikari.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.*;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.annotation.*;

import javax.sql.*;
import java.util.*;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"${com.cbdc.common.config.repositoryPackageName}", "com.opl.cbdc.config.repository","com.opl.cbdc.utils.itr.common.repository"}, entityManagerFactoryRef = "usersDataStoreEM", transactionManagerRef = "usersDataStoreTM")
public class PersistenceContext {

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_LAZY_LOAD = "hibernate.enable_lazy_load_no_trans";

    private static final String PROPERTY_NAME_HIBERNATE_PROC_PARAM_NULL_PASSING = "hibernate.proc.param_null_passing";

    @Autowired
    private ApplicationProperties properties;

    @Bean(name = "usersDataStore")
    @Primary
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getDbDriver());
        dataSource.setJdbcUrl(DataSourceProvider.getDatabaseName() + properties.getDbUrl());
        dataSource.setUsername(DataSourceProvider.getUserName());
        dataSource.setPassword(DataSourceProvider.getPassword());
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setMaximumPoolSize(Integer.parseInt(properties.getDbMaxConnections()));
        dataSource.setMaxLifetime(Long.parseLong(properties.getDbMaxLifetimeInMillis()));
        dataSource.setConnectionTimeout(Long.parseLong(properties.getDbConnectionTimeoutInMillis()));
//        dataSource.setMinimumIdle(5);
//        dataSource.setIdleTimeout(10000);
        return dataSource;
    }

    @Bean(name = "usersDataStoreTM")
    @DependsOn("usersDataStore")
    @Primary
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean(name = "usersDataStoreEM")
    @DependsOn("usersDataStore")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());  
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan(properties.getDomainPackageName(), "com.opl.cbdc.config.domain","com.opl.cbdc.utils.itr.common.domain");

        Properties jpaProperties = new Properties();
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, properties.getHibernateDialect());
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, properties.getHibernateFormatSql());
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, properties.getHibernateHbm2ddl());
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, properties.getHibernateEJBNamingStrategy());
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, properties.getHibernateShowSql());
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_LAZY_LOAD, properties.getHibernateEnableLazyLoadNoTrans());
        jpaProperties.put(PROPERTY_NAME_HIBERNATE_PROC_PARAM_NULL_PASSING, properties.getHibernateParamNullPassing());
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }
}