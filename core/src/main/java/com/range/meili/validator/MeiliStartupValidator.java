package com.range.meili.validator;

import com.range.meili.exception.MeiliNotStartedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * Database validator for Meilisearch.
 * Blocks Spring Boot startup until Meilisearch is fully ready,
 * including snapshot import completion.
 */
public class MeiliStartupValidator {

    private static final Logger log =
            LoggerFactory.getLogger(MeiliStartupValidator.class);

    private final MeiliHealthChecker healthChecker;
    private final MeiliTaskChecker taskChecker;
    private final MeiliIndexChecker indexChecker;

    private final int timeoutSeconds;
    private final int intervalSeconds;

    public MeiliStartupValidator(
            MeiliHealthChecker healthChecker,
            MeiliTaskChecker taskChecker,
            MeiliIndexChecker indexChecker,
            int timeoutSeconds,
            int intervalSeconds
    ) {
        this.healthChecker = healthChecker;
        this.taskChecker = taskChecker;
        this.indexChecker = indexChecker;
        this.timeoutSeconds = timeoutSeconds;
        this.intervalSeconds = intervalSeconds;
    }

    public void validate() {

        Instant deadline = Instant.now().plusSeconds(timeoutSeconds);

        log.info("MeiliSearch startup validation started (timeout={}s)", timeoutSeconds);

        while (Instant.now().isBefore(deadline)) {

            if (!healthChecker.isHealthy()) {

                log.debug("Health check failed");

            } else if (!taskChecker.isSnapshotFinished()) {

                log.warn("Snapshot import still running");

            } else if (!indexChecker.isQueryable()) {

                log.info("Indexes are not queryable yet");

            } else {

                log.info("MeiliSearch is fully ready");
                return;
            }

            sleep();
        }

        log.error("Startup timeout reached ({}s)", timeoutSeconds);

        throw new MeiliNotStartedException(
                "MeiliSearch is not ready after " + timeoutSeconds + " seconds"
        );
    }

    private void sleep() {
        try {
            Thread.sleep(intervalSeconds * 1000L);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Startup validation interrupted", e);
        }
    }
}
