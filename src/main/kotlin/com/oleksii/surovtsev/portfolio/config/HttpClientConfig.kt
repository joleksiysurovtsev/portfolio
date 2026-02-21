package com.oleksii.surovtsev.portfolio.config

import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * Configuration for HTTP clients used throughout the application.
 * Provides properly configured OkHttpClient beans with appropriate timeouts.
 */
@Configuration
class HttpClientConfig {

    @Value("\${http.client.connect-timeout:10}")
    private val connectTimeout: Long = 10

    @Value("\${http.client.read-timeout:30}")
    private val readTimeout: Long = 30

    @Value("\${http.client.write-timeout:10}")
    private val writeTimeout: Long = 10

    @Value("\${http.client.call-timeout:45}")
    private val callTimeout: Long = 45

    /**
     * Creates a configured OkHttpClient bean for general use.
     * Includes proper timeout settings for production environments.
     */
    @Bean
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .callTimeout(callTimeout, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * Creates a specialized OkHttpClient for GitHub API calls.
     * GitHub GraphQL queries may take longer, so we allow more time.
     */
    @Bean(name = ["githubHttpClient"])
    fun githubHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout * 2, TimeUnit.SECONDS) // Double read timeout for GraphQL
            .writeTimeout(writeTimeout, TimeUnit.SECONDS)
            .callTimeout(callTimeout * 2, TimeUnit.SECONDS) // Double call timeout
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * Creates a specialized OkHttpClient for quick API calls.
     * Used for health checks and quick status endpoints.
     */
    @Bean(name = ["quickHttpClient"])
    fun quickHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .callTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()
    }
}