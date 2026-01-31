package com.range;

import com.range.meili.validator.MeiliStartupValidator;
import org.springframework.context.SmartLifecycle;
import org.springframework.core.Ordered;

public class MeiliStartupLifecycle implements SmartLifecycle {

    private final MeiliStartupValidator validator;
    private boolean running = false;

    public MeiliStartupLifecycle(MeiliStartupValidator validator) {
        this.validator = validator;
    }

    @Override
    public void start() {
        validator.validate();
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return Integer.MIN_VALUE;
    }


}