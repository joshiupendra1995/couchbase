package com.uj.couchbase.configrepo;

import com.couchbase.client.core.env.PasswordAuthenticator;
import com.couchbase.client.core.env.SecurityConfig;
import com.couchbase.client.core.env.TimeoutConfig;
import com.couchbase.client.java.*;
import com.couchbase.client.java.env.ClusterEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class CouchbaseConfiguration {

    private static final String SCOPE = "_default";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.couchbase.bootstrap-hosts}")
    private String hostUrl;
    @Value("${spring.couchbase.username}")
    private String userName;
    @Value("${spring.couchbase.password}")
    private String passwordString;
    @Value("${couchbase.bucket.names}")
    private List<String> buckets;
    @Value("${spring.couchbase.env.ssl.enabled}")
    private boolean sslEnabled;
    @Value("${couchbase.connection.timeout}")
    private long connectionTimeout;
    private Cluster cluster;

    /**
     * Configures the Couchbase cluster environment.
     *
     * @return The configured Couchbase cluster environment.
     */
    public ClusterEnvironment couchbaseClusterEnvironment() {
        try {
            ClusterEnvironment.Builder clEnvBuilder = ClusterEnvironment.builder();
            ClusterEnvironment clusterEnv;
            TimeoutConfig.Builder timeoutConfig = clEnvBuilder.timeoutConfig().connectTimeout(Duration.ofSeconds(connectionTimeout));
            if (sslEnabled) {
                logger.debug("SSL/TLS is enabled: {}", sslEnabled);
                clusterEnv = clEnvBuilder.timeoutConfig(timeoutConfig).securityConfig(SecurityConfig.enableTls(sslEnabled).enableHostnameVerification(false)).build();

                logger.debug("Connecting with couchbase with TLS enabled {}", SecurityConfig.enableTls(sslEnabled));

            } else {
                logger.debug("Connecting with Couchbase without TLS enabled");
                return clEnvBuilder.timeoutConfig(timeoutConfig).securityConfig(SecurityConfig.enableTls(false)).build();
            }

            return clusterEnv;
        } catch (Exception e) {
            logger.error("Error while configuring Couchbase cluster environment: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * @return reactiveCollections
     */
    @Bean
    public Map<String, ReactiveCollection> getReactiveCollections() {
        Map<String, ReactiveCollection> reactiveCollections = new HashMap<>();
        buckets.forEach(bucket -> reactiveCollections.put(bucket, getReactiveCollection(bucket)));
        return reactiveCollections;
    }

    /**
     * Retrieves a {@link ReactiveCollection} for the specified Couchbase bucket.
     *
     * @param bucketName The name of the Couchbase bucket.
     * @return A {@link ReactiveCollection} for the specified bucket.
     * @throws RuntimeException if an error occurs during the connection establishment.
     */
    private ReactiveCollection getReactiveCollection(String bucketName) {
        cluster = getCouchbaseCluster();
        logger.debug("Establishing connection for Bucket:{}", bucketName);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(SCOPE);
        Collection collection = scope.collection(SCOPE);
        return collection.reactive();
    }

    /**
     * @return reactive cluster using Cluster.reactive()
     */

    @Bean
    public ReactiveCluster getReactiveCollectionForQuery() {
        cluster = getCouchbaseCluster();
        return cluster.reactive();
    }

    /**
     * Retrieves or initializes the Couchbase cluster.
     *
     * @return The Couchbase cluster.
     * @throws RuntimeException if an error occurs while initializing the Couchbase cluster.
     */
    @Bean
    public Cluster getCouchbaseCluster() {
        try {
            if (cluster == null) {
                logger.debug("Initializing Couchbase cluster connection...");
                PasswordAuthenticator authenticator = PasswordAuthenticator.builder().username(userName).password(passwordString).build();
                ClusterOptions clusterOptions = ClusterOptions.clusterOptions(authenticator).environment(couchbaseClusterEnvironment());
                cluster = Cluster.connect(hostUrl, clusterOptions);
                cluster.waitUntilReady(Duration.ofSeconds(15));
                logger.debug("Couchbase cluster connection initialized.");
            }
            return cluster;
        } catch (Exception e) {
            logger.error("Error while initializing Couchbase cluster: {}", e.getMessage(), e);
            throw new RuntimeException("Error while initializing Couchbase cluster", e);
        }
    }


}
