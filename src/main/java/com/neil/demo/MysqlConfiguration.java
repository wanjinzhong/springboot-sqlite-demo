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
    basePackages = "com.neil.demo.repository.main",
    transactionManagerRef = "primaryTransactionManager",
    entityManagerFactoryRef = "mysqlContainerEntityManagerFactoryBean"
)
@EnableTransactionManagement
public class MysqlConfiguration {

    @Autowired
    @Bean
    public JpaTransactionManager primaryTransactionManager(@Qualifier(value = "primaryDataSource") DataSource dataSource,
                                                           @Qualifier(value = "mysqlContainerEntityManagerFactoryBean")
                                                               EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager
            = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }

    @Autowired
    @Bean
    LocalContainerEntityManagerFactoryBean mysqlContainerEntityManagerFactoryBean(@Qualifier(value = "primaryDataSource") DataSource dataSource,
                                                                                  @Qualifier(value = "primaryVendorAdapter")
                                                                                      JpaVendorAdapter primaryVendorAdapter) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
            = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.neil.demo.entity.main");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(primaryVendorAdapter);
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter primaryVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return hibernateJpaVendorAdapter;
    }

}
