package com.range.meili.http;

import static org.junit.jupiter.api.Assertions.*;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MeiliHttpClientTest {

    private MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    void get_shouldReturnBody_whenResponseIs200() throws Exception {

        server.enqueue(new MockResponse().setResponseCode(200).setBody("OK"));

        MeiliHttpClient client = new MeiliHttpClient(null);
        String url = server.url("/health").toString();
        String response = client.get(url);


        assertEquals("OK", response);
    }

    @Test
    void get_shouldThrowException_whenResponseIsNotSuccessful() {

        server.enqueue(new MockResponse().setResponseCode(500));

        MeiliHttpClient client = new MeiliHttpClient(null);
        String url = server.url("/health").toString();


        assertThrows(IOException.class, () -> client.get(url));
    }

    @Test
    void isOk_shouldReturnTrue_whenServiceIsUp() {

        server.enqueue(new MockResponse().setResponseCode(200).setBody("healthy"));

        MeiliHttpClient client = new MeiliHttpClient(null);
        String url = server.url("/health").toString();


        boolean ok = client.isOk(url);


        assertTrue(ok);
    }

    @Test
    void isOk_shouldReturnFalse_whenServiceIsDown() {

        server.enqueue(new MockResponse().setResponseCode(503));

        MeiliHttpClient client = new MeiliHttpClient(null);
        String url = server.url("/health").toString();


        boolean ok = client.isOk(url);


        assertFalse(ok);
    }

    @Test
    void shouldSendAuthorizationHeader_whenApiKeyProvided() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("OK"));

        MeiliHttpClient client = new MeiliHttpClient("secret-key");

        String url = server.url("/health").toString();

        client.get(url);
        var recordedRequest = server.takeRequest();
        assertEquals("Bearer secret-key", recordedRequest.getHeader("Authorization"));
    }
}
