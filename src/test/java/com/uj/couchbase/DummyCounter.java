package com.uj.couchbase;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class DummyCounter {

    @Test
    void testDummyCounter() {
        Flux.generate(() -> 0, (counter, sink) -> {
            counter = counter + 1;
            sink.next(counter);
            if(counter>9) {
                sink.complete();
            }
            return counter;
        }).subscribe(System.out::println);
    }

    @Test
    void testDummyCounterHandle() {
       Flux<Object> evenNumbers = Flux.range(1,10).handle((value , syk)->{
           if(value % 2 == 0){
               if(value == 4){
                   syk.complete();
                   return;
               }
               syk.next(value);
           }
       }).log();

        StepVerifier.create(evenNumbers)
                .expectNext(2)
                .verifyComplete();
    }
}
