package com.neil.demo;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.neil.demo.entity.main.Test;
import com.neil.demo.repository.main.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @RequestMapping(value = "save", method = RequestMethod.GET)
    public String test(String a, String b) {
        Test test = new Test();
        test.setA(a);
        test.setB(b);
        testRepository.save(test);
        return "OK";
    }

    @RequestMapping(value = "query", method = RequestMethod.GET)
    public String query() {
        List<Test> tests = testRepository.findAll();
        return JSON.toJSON(tests).toString();
    }
}
