package com.uj.couchbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

@SpringBootApplication
@EnableReactiveCouchbaseRepositories
public class CouchbasePocApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouchbasePocApplication.class, args);
	}

}
