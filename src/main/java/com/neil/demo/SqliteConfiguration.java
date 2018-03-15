package com.neil.demo;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
    basePackages = "com.neil.demo.repository.sqlite",
    transactionManagerRef = "jpaTransactionManager",
    entityManagerFactoryRef = "sqliteEntityManagerFactoryBean"
)
@EnableTransactionManagement
public class SqliteConfiguration {

    @Autowired
    @Bean
    public JpaTransactionManager sqliteTransactionManager(@Qualifier(value = "sqliteDataSource") DataSource dataSource,
                                                          @Qualifier(value = "sqliteEntityManagerFactoryBean") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager
            = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }

    @Autowired
    @Bean
    LocalContainerEntityManagerFactoryBean sqliteEntityManagerFactoryBean(@Qualifier(value = "sqliteDataSource") DataSource dataSource,
                                                                          @Qualifier(value = "sqliteVendorAdapter") JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
            = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.neil.demo.entity.sqlite");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter sqliteVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("com.enigmabridge.hibernate.dialect.SQLiteDialect");
        return hibernateJpaVendorAdapter;
    }
}