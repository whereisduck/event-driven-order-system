package com.duck.ordersystem.financeservice.client;

import com.duck.ordersystem.financeservice.repository.InvoiceRepository;
import com.duck.ordersystem.financeservice.service.InvoiceService;
import com.duck.ordersystem.financeservice.util.webClient.WebClientConfig;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.in;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.assertj.core.api.Assertions.assertThat;


public class CatalogServiceClientTest {
    @Autowired
    InvoiceRepository invoiceRepository;
    private static final String CATALOGUE_SERVICE_URI = "/products/";
    final MockWebServer mockWebServer = new MockWebServer();

    WebClientConfig webClientConfig = new WebClientConfig();
    WebClient.Builder catalogWeb;
    InvoiceService invoiceService;

    String itemId = "item-1";

    @BeforeEach
    void setup() {
        this.catalogWeb = webClientConfig.CatalogwebClientBuilder(mockWebServer.url(CATALOGUE_SERVICE_URI).toString());
         this.invoiceService = new InvoiceService(invoiceRepository, catalogWeb);

    }
    @AfterEach
    void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    void finProductItem() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"id\": \"item-1\", \"price\": 2.5}")
                .setHeader(HttpHeaders.CONTENT_TYPE,APPLICATION_JSON_VALUE));
        var foundItem = invoiceService.findProductItem(itemId);
        StepVerifier
                .create(foundItem)
                .expectNextMatches(item -> item.getId().equals(itemId) && item.getPrice().equals(BigDecimal.valueOf(2.5)))
                .verifyComplete();
        var recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getHeader(HttpHeaders.CONTENT_TYPE)).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(recordedRequest.getHeader(HttpHeaders.ACCEPT)).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(recordedRequest.getPath()).isEqualTo("/products/item-1");
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");

    }

    @Test
    void findProductItemWhenReturnsError() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(400)
                .setBody("{\"message\": \"error-message\"}")
                .setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE));

        var foundItem = invoiceService.findProductItem(itemId);

        StepVerifier
                .create(foundItem)
                .verifyErrorMatches(error -> error.getMessage().equals("error-message"));

        var recordedRequest = mockWebServer.takeRequest();
        assertThat(recordedRequest.getHeader(HttpHeaders.CONTENT_TYPE)).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(recordedRequest.getHeader(HttpHeaders.ACCEPT)).isEqualTo(APPLICATION_JSON_VALUE);
        assertThat(recordedRequest.getPath()).isEqualTo("/products/item-1");
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
    }






}
