package com.ms.framework.stepdefinitions;

import com.ms.framework.config.ServiceConfig;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = ServiceConfig.class)
public class Hooks {
}
