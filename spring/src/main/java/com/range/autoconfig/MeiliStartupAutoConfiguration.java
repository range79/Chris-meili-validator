package com.range.autoconfig;

import com.range.meili.http.MeiliHttpClient;
import com.range.meili.validator.MeiliHealthChecker;
import com.range.meili.validator.MeiliIndexChecker;
import com.range.meili.validator.MeiliStartupValidator;
import com.range.meili.validator.MeiliTaskChecker;
import com.range.properties.MeiliStartupProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MeiliStartupProperties.class)
public class MeiliStartupAutoConfiguration {

    @Bean
    public MeiliHttpClient meiliHttpClient(MeiliStartupProperties properties) {
        return new MeiliHttpClient(properties.getApiKey());
    }

    @Bean
    public MeiliStartupValidator meiliStartupValidator(
            MeiliHttpClient httpClient,
            MeiliStartupProperties properties
    ) {
        MeiliHealthChecker healthChecker = new MeiliHealthChecker(httpClient, properties.getUrl());
        MeiliTaskChecker taskChecker = new MeiliTaskChecker(httpClient, properties.getUrl());
        MeiliIndexChecker indexChecker = new MeiliIndexChecker(httpClient, properties.getUrl());

        MeiliStartupValidator validator = new MeiliStartupValidator(
                healthChecker,
                taskChecker,
                indexChecker,
                properties.getTimeout(),
                properties.getInterval()
        );


        validator.validate();
        return validator;

    }


}
