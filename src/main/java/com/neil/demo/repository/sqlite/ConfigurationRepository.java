package com.neil.demo.repository.sqlite;
import java.util.List;

import com.neil.demo.entity.sqlite.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {
    List<Configuration> findByKey(String key);

}
