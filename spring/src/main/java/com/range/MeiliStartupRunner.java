package com.range;

import com.range.meili.validator.MeiliStartupValidator;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MeiliStartupRunner implements ApplicationRunner {

    private final MeiliStartupValidator validator;

    public MeiliStartupRunner(MeiliStartupValidator validator) {
        this.validator = validator;
    }

    @Override

    public void run(@NonNull ApplicationArguments args) {
        validator.validate();
    }
}
