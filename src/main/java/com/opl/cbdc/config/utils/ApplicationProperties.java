package com.opl.cbdc.config.utils;

import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "com.cbdc.common.config")
public class ApplicationProperties {

	// DB CONFIGURATION PROPERTIES
	private String dbDriver;
	private String dbUrl;
	private String dbUrlPushPull;
	private String dbMaxConnections;
	private String dbMinConnections;
	private String dbMaxPartitions;
	private String dbMaxLifetimeInMillis;
	private String dbConnectionTimeoutInMillis;

	// HIBERNATE PROPERTIES
	private String hibernateDialect;
	private String hibernateFormatSql;
	private String hibernateHbm2ddl;
	private String hibernateEJBNamingStrategy;
	private String hibernateShowSql;
	private String hibernateEnableLazyLoadNoTrans;
	private String hibernateParamNullPassing;

	// DOMAIN AND REPOSITORY PROPERTIES
	private String domainPackageName;
	private String repositoryPackageName;

	public String getDbDriver() {
		return dbDriver;
	}

	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbMaxConnections() {
		return dbMaxConnections;
	}

	public void setDbMaxConnections(String dbMaxConnections) {
		this.dbMaxConnections = dbMaxConnections;
	}

	public String getDbMinConnections() {
		return dbMinConnections;
	}

	public void setDbMinConnections(String dbMinConnections) {
		this.dbMinConnections = dbMinConnections;
	}

	public String getDbMaxPartitions() {
		return dbMaxPartitions;
	}

	public void setDbMaxPartitions(String dbMaxPartitions) {
		this.dbMaxPartitions = dbMaxPartitions;
	}

	public String getDbMaxLifetimeInMillis() {
		return dbMaxLifetimeInMillis;
	}

	public void setDbMaxLifetimeInMillis(String dbMaxLifetimeInMillis) {
		this.dbMaxLifetimeInMillis = dbMaxLifetimeInMillis;
	}

	public String getDbConnectionTimeoutInMillis() {
		return dbConnectionTimeoutInMillis;
	}

	public void setDbConnectionTimeoutInMillis(String dbConnectionTimeoutInMillis) {
		this.dbConnectionTimeoutInMillis = dbConnectionTimeoutInMillis;
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}

	public String getHibernateFormatSql() {
		return hibernateFormatSql;
	}

	public void setHibernateFormatSql(String hibernateFormatSql) {
		this.hibernateFormatSql = hibernateFormatSql;
	}

	public String getHibernateHbm2ddl() {
		return hibernateHbm2ddl;
	}

	public void setHibernateHbm2ddl(String hibernateHbm2ddl) {
		this.hibernateHbm2ddl = hibernateHbm2ddl;
	}

	public String getHibernateEJBNamingStrategy() {
		return hibernateEJBNamingStrategy;
	}

	public void setHibernateEJBNamingStrategy(String hibernateEJBNamingStrategy) {
		this.hibernateEJBNamingStrategy = hibernateEJBNamingStrategy;
	}

	public String getHibernateShowSql() {
		return hibernateShowSql;
	}

	public void setHibernateShowSql(String hibernateShowSql) {
		this.hibernateShowSql = hibernateShowSql;
	}

	public String getHibernateEnableLazyLoadNoTrans() {
		return hibernateEnableLazyLoadNoTrans;
	}

	public void setHibernateEnableLazyLoadNoTrans(String hibernateEnableLazyLoadNoTrans) {
		this.hibernateEnableLazyLoadNoTrans = hibernateEnableLazyLoadNoTrans;
	}

	public String getHibernateParamNullPassing() {
		return hibernateParamNullPassing;
	}

	public void setHibernateParamNullPassing(String hibernateParamNullPassing) {
		this.hibernateParamNullPassing = hibernateParamNullPassing;
	}

	public String getDomainPackageName() {
		return domainPackageName;
	}

	public void setDomainPackageName(String domainPackageName) {
		this.domainPackageName = domainPackageName;
	}

	public String getRepositoryPackageName() {
		return repositoryPackageName;
	}

	public void setRepositoryPackageName(String repositoryPackageName) {
		this.repositoryPackageName = repositoryPackageName;
	}

	public String getDbUrlPushPull() {
		return dbUrlPushPull;
	}

	public void setDbUrlPushPull(String dbUrlPushPull) {
		this.dbUrlPushPull = dbUrlPushPull;
	}

}
