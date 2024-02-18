package com.uj.couchbase.service;

import com.uj.couchbase.configrepo.CouchbaseTemplateComponent;
import com.uj.couchbase.model.Request;
import com.uj.couchbase.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CouchbaseService {
    @Autowired
    private CouchbaseTemplateComponent repository;

    public Mono<Response> getAndTouch(String document, String bucket) {
        return repository.findById(document,bucket).flatMap(data -> {
            Response response = new Response("Success", document);
            response.setData(data);
            return Mono.just(response);
        });
    }

    public Mono<Response> createOrUpdate(Request request) {
        return repository.createOrUpdate(request.getBucket(), request.getDocument(), request.getTimeToLive(), request.getData())
                .then(Mono.just(new Response("Success", request.getDocument())));
    }

    public Mono<Response> delete(String bucket, String document) {
        return repository.deleteById(document, bucket)
                .then(Mono.just(new Response("Success",document)));
    }
}
