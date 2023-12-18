package com.uj.couchbase.config;

import com.couchbase.client.core.env.IoConfig;
import com.couchbase.client.java.env.ClusterEnvironment;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

@Configuration
@EnableReactiveCouchbaseRepositories
@Getter
public class CouchBaseConfig extends AbstractCouchbaseConfiguration {

    @Value("${couchbase.hostname}")
    public String connectionString;

    @Value("${couchbase.bucket.name}")
    public String bucketName;

    @Value("${couchbase.username}")
    public String userName;

    @Value("${couchbase.password}")
    public String password;


    @Override
    protected void configureEnvironment(final ClusterEnvironment.Builder builder) {
        builder.securityConfig().enableTls(true);
        builder.ioConfig().enableDnsSrv(true).build();
    }

}
