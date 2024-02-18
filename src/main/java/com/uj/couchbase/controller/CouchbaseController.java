package com.uj.couchbase.controller;

import com.uj.couchbase.model.Response;
import com.uj.couchbase.service.CouchbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/datastore")
public class CouchbaseController {

    @Autowired
    private CouchbaseService couchbaseService;

    @GetMapping("/getAndTouch/{document}/{bucket}")
    public Mono<Response> getAndTouch(@PathVariable String document, @PathVariable String bucket) {
        return couchbaseService.getAndTouch(document, bucket);
    }
}
