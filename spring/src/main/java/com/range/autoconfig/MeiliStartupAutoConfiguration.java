package com.range.autoconfig;

import com.range.MeiliStartupInitializingBean;
import com.range.properties.MeiliStartupProperties;
import com.range.validator.MeiliStartupValidator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MeiliStartupProperties.class)
public class MeiliStartupAutoConfiguration {

    @Bean
    public MeiliStartupValidator meiliStartupValidator(
            MeiliStartupProperties properties
    ) {
        MeiliStartupValidator validator = new MeiliStartupValidator();
        validator.setDataSourceURL(properties.getUrl());
        validator.setTimeout(properties.getTimeout());
        validator.setInterval(properties.getInterval());
        return validator;
    }

    @Bean
    public MeiliStartupInitializingBean meiliStartupInitializingBean(
            MeiliStartupValidator validator
    ) {
        return new MeiliStartupInitializingBean(validator);
    }
}
