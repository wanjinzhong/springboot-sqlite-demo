package com.neil.demo;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.neil.demo.entity.sqlite.Configuration;
import com.neil.demo.repository.sqlite.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "config")
public class ConfigController {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String test(String key, String value) {
        Configuration configuration = new Configuration();
        configuration.setKey(key);
        configuration.setValue(value);
        configurationRepository.save(configuration);
        return "OK";
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public String query() {
        List<Configuration> configurations = configurationRepository.findAll();
        int a = 1 / 0;
        return JSON.toJSON(configurations).toString();
    }

    @RequestMapping(value = "update", method = RequestMethod.GET)
    public String update(String key, String value) {
        List<Configuration> configurations = configurationRepository.findByKey(key);
        if (!CollectionUtils.isEmpty(configurations)) {
            Configuration configuration = configurations.get(0);
            configuration.setValue(value);
            configurationRepository.save(configuration);
        }
        return "OK";
    }

    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public String update(String key) {
        List<Configuration> configurations = configurationRepository.findByKey(key);
        configurationRepository.deleteAll(configurations);
        return "OK";
    }
}
