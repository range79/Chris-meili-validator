package com.range.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "meili.startup")
public class MeiliStartupProperties {


    private String url = "http://localhost:7070";

    /**
     * Max wait time (seconds)
     */
    private int timeout = 30;

    /**
     * Poll interval (seconds)
     */
    private int interval = 1;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
