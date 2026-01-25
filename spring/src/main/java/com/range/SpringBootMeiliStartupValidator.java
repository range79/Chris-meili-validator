package com.range;

import com.range.validator.MeiliStartupValidator;
import org.springframework.beans.factory.InitializingBean;

public class SpringBootMeiliStartupValidator
        extends MeiliStartupValidator
        implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        validateDatabase();
    }
}
