package com.uj.couchbase.configrepo;

import com.couchbase.client.java.ReactiveCollection;
import com.couchbase.client.java.kv.GetAndTouchOptions;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.Map;

@Component
public class CouchbaseTemplateComponent {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.couchbase.bootstrap-hosts}")
    private String hostUrl;
    @Value("${spring.couchbase.username}")
    private String userName;
    @Value("${spring.couchbase.password}")
    private String passwordString;
    @Value("${spring.couchbase.expiry.inSeconds}")
    private int expiryInSeconds;
    @Value("${couchbase.operation.timeout}")
    private int couchbaseOperationTimeoutInMs;
    @Autowired
    private CouchbaseConfiguration configuration;
    @Autowired
    private Map<String, ReactiveCollection> reactiveCollections;

    public Mono<JsonNode> findById(String document, String bucket) {
        logger.info("Inside findById() method of {}", bucket);
        return reactiveCollections.get(bucket).getAndTouch(document, Duration.ofSeconds(expiryInSeconds), GetAndTouchOptions.getAndTouchOptions().timeout(Duration.ofMillis(couchbaseOperationTimeoutInMs))).flatMap(getResult -> Mono.just(getResult.contentAs(JsonNode.class))).doOnError(throwable -> {
            logger.error("Error occurred {} ", throwable.getMessage());
            throw new RuntimeException("Document not found {}", throwable);
        });
    }

}

