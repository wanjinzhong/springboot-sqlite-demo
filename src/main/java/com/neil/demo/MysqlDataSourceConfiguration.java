package com.neil.demo;
import java.util.List;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.neil.demo.entity.sqlite.Configuration;
import com.neil.demo.repository.sqlite.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class MysqlDataSourceConfiguration {
    @Autowired
    private ConfigurationRepository configurationRepository;

    @Bean(destroyMethod = "", name = "primaryDataSource")
    public DataSource primaryataSource() {
        List<Configuration> all = configurationRepository.findAll();
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
                    dataSourceBuilder.url("jdbc:mysql://localhost:3306/testd");
                }
            }
        } catch (Exception e) {
            System.out.println("数据库不正确");
        }
        return dataSourceBuilder.build();
    }
}
