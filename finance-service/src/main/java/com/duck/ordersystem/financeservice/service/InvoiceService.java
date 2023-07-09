package com.duck.ordersystem.financeservice.service;

import com.duck.ordersystem.financeservice.model.*;
import com.duck.ordersystem.financeservice.repository.InvoiceRepository;
import com.duck.ordersystem.financeservice.util.ApiErrorResponse;
import com.duck.ordersystem.financeservice.util.OrderNoFound;
import com.duck.ordersystem.financeservice.util.ServiceApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final WebClient.Builder catalogWeb;


    public Mono<InvoiceLine> createInVoiceLine(ItemLine itemLine){
              return  findProductItem(itemLine.getItemId())
                .switchIfEmpty(Mono.error(new OrderNoFound(itemLine.getItemId())))
                .map(ls -> new InvoiceLine(ls, itemLine.getQuantity()));


    }

    public Mono<ProductItem> findProductItem(String itemId) {
        return catalogWeb.build()
                .get()
                .uri(builder -> builder.path("/"+itemId).build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, InvoiceService::mapErrorResponse)
                .bodyToMono(ProductItem.class);
    }

    private static Mono<? extends Throwable> mapErrorResponse(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ApiErrorResponse.class)
                .flatMap(error -> Mono.error(new ServiceApiException(clientResponse.statusCode(), error.getMessage())));
    }

    public Mono<Invoice> save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }



}
