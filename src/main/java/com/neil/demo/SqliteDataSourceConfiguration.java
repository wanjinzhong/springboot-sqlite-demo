package com.neil.demo;
import java.util.List;

import javax.sql.DataSource;

import com.neil.demo.repository.sqlite.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.sqlite.SQLiteDataSource;

@org.springframework.context.annotation.Configuration
public class SqliteDataSourceConfiguration {

    @Bean(destroyMethod = "", name = "sqliteDataSource")
    @Primary
    public DataSource sqliteDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:" + "example.db");
        dataSourceBuilder.type(SQLiteDataSource.class);
        return dataSourceBuilder.build();
    }

    // @Bean(destroyMethod = "", name = "primaryataSource")
    // public DataSource primaryataSource() {
    //     List<com.neil.demo.entity.sqlite.Configuration> all = configurationRepository.findAll();
    //     DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
    //     for (com.neil.demo.entity.sqlite.Configuration configuration : all) {
    //         if ("username".equals(configuration.getKey())) {
    //             dataSourceBuilder.username(configuration.getValue());
    //         }
    //         if ("password".equals(configuration.getKey())) {
    //             dataSourceBuilder.password(configuration.getValue());
    //         }
    //         if ("type".equals(configuration.getKey())) {
    //             // dataSourceBuilder.type(DataSource.class);
    //             dataSourceBuilder.url("jdbc:mysql://localhost:3306/test");
    //         }
    //     }
    //     return dataSourceBuilder.build();
    // }
}