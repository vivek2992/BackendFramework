package com.ms.framework.runner;

import com.ms.framework.util.CommonUtils;
import com.ms.framework.world.CommonWorld;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources",
        glue = "com.ms.framework.stepdefinitions",
        tags = "@ScenarioName",
        plugin = {
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        "json:test-output/json/cucumber-report.json",
        "junit:test-output/xml/cucumber-report.xml",
        "pretty"},
        monochrome = false,
        publish = false
)

@Component
public class TestRunner {
    static CommonWorld cw;
    static CommonUtils cu;
    static Environment env;
    @Autowired
    private CommonWorld tcw;
    @Autowired
    private CommonUtils tcu;
    @Autowired
    private Environment tenv;

    @PostConstruct
    public void init(){
        TestRunner.cw=tcw;
        TestRunner.cu=tcu;
        TestRunner.env=tenv;
    }
    @AfterClass
    public static void exit(){
        System.out.println("Test Completed");
    }
}
