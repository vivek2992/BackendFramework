package com.ms.framework.world;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CommonWorld {
    private final String systemUserName=System.getProperty("user.name");
    @Value("${cucumber.json.report}")
    private String jsonReport;
    @Value("${selenix.spark.report}")
    private String htmlReport;
}
