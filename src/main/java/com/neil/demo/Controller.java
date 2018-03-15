package com.neil.demo;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.neil.demo.entity.sqlite.Configuration;
import com.neil.demo.repository.sqlite.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

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
        return JSON.toJSON(configurations).toString();
    }
}
