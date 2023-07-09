package com.duck.ordersystem.catalogservice.util;

import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class test {
    public static void main(String[] args) {
        /*Flux.just(2, 7, 10)
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Mono.just(12))
                .log()
                .subscribe(System.out::println);

    }

         */
        /*
        Flux.just(2, 0,7, 0,10)
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .map(l -> 10/l)
                .concatWith(Mono.just(12))
                .onErrorReturn(72)
                .log()
                .subscribe();

    }

         */
        /*

        Flux.just(2, 7, 10)
                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Mono.just(12))
                .onErrorResume(err -> {
                    System.out.println("Error caught: " + err);
                    return Mono.just(72);
                })
                .log()
                .subscribe();

    }
    */

    Flux.just(2, 7, 10, 8, 12, 22, 24)
            .map(element -> {
                if (element == 8) {
                  throw new RuntimeException("Exception occurred!");
                }
                return element;
            }).onErrorContinue((ex, element) ->{

                System.out.println("Exception caught: " + ex);
                System.out.println("The element that caused the exception is: " + element);})
               .log()
            .doAfterTerminate(()->System.out.println("after TERMINATE"))
            .doOnComplete(()->System.out.println("on complete"))
            .doOnSubscribe(System.out::println)
            .doOnTerminate(()->System.out.println("on TERMINATE"))

            .subscribe();


        /*

        Flux.just(2, 7, 10, 8, 12, 22, 24)
                .map(element -> {
                    if (element == 8) {
                        throw new RuntimeException("Exception occurred!");
                    }
                    return element;
                }).onErrorMap(ex -> {
                    System.out.println("Exception caught: " + ex);
                    return new ServiceApiException(HttpStatus.NOT_FOUND,ex.getMessage());
                }).log()
                .subscribe();

    }

         */




/*
            Flux.just(1, 2, 3)
                    .concatWith(Flux.error(new RuntimeException("Exception occurred.")))
                    .concatWith(Mono.just(12))

                    .doOnError(ex -> System.out.println("Exception caught: " + ex)) // catch and print the exception
                    .log()
                    .subscribe();

        }

 */

    }
}