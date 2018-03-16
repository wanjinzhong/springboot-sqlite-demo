package com.neil.demo;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.neil.demo.repository.sqlite.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
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
public class MysqlDataSourceConfiguration {
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Bean(destroyMethod = "", name = "primaryDataSource")
    public DataSource primaryataSource() {
        List<com.neil.demo.entity.sqlite.Configuration> all = configurationRepository.findAll();
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        try {
            for (com.neil.demo.entity.sqlite.Configuration configuration : all) {
                if ("username".equals(configuration.getKey())) {
                    dataSourceBuilder.username(configuration.getValue());
                }
                if ("password".equals(configuration.getKey())) {
                    dataSourceBuilder.password(configuration.getValue());
                }
                if ("type".equals(configuration.getKey())) {
                    dataSourceBuilder.type(ComboPooledDataSource.class);
                    dataSourceBuilder.url("jdbc:mysql://localhost:3306/tesdt?characterEncoding=utf8&useSSL=true");
                }
            }
        } catch (Exception e) {
            System.out.println("数据库不正确");
        }
        // return null;
        return dataSourceBuilder.build();
    }


    @Bean
    public JpaTransactionManager primaryTransactionManager(@Autowired @Qualifier(value = "primaryDataSource") DataSource dataSource,
                                                           @Autowired @Qualifier(value = "mysqlContainerEntityManagerFactoryBean")
                                                               EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager
            = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        jpaTransactionManager.setDataSource(dataSource);
        return jpaTransactionManager;
    }


    @Bean
    LocalContainerEntityManagerFactoryBean mysqlContainerEntityManagerFactoryBean(@Autowired @Qualifier(value = "primaryDataSource") DataSource primaryDataSource) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean
            = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(primaryDataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.neil.demo.entity.main");
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        // hibernateJpaVendorAdapter.setGenerateDdl(true);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        return localContainerEntityManagerFactoryBean;
    }


}
