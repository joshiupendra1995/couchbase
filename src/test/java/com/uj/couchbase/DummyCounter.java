package com.uj.couchbase;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

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
}
