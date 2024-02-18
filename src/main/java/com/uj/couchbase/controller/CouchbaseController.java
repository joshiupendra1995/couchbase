package com.uj.couchbase.controller;

import com.uj.couchbase.model.Request;
import com.uj.couchbase.model.Response;
import com.uj.couchbase.service.CouchbaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public Mono<Response> create(@RequestBody Request request){
        return couchbaseService.createOrUpdate(request);
    }

    @DeleteMapping("/{bucket}/{document}")
    public Mono<Response> delete(@PathVariable String bucket, @PathVariable String document){
        return couchbaseService.delete(bucket,document);
    }

}
