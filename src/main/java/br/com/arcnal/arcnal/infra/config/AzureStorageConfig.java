package br.com.arcnal.arcnal.infra.config;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AzureStorageConfig {
    @Value("${spring.cloud.azure.storage.blob.account-name}")
    private String accountName;

    @Value("${spring.cloud.azure.storage.blob.account-key}")
    private String accountKey;

    @Value("${spring.cloud.azure.storage.blob.endpoint}")
    private String endpoint;

    @Bean
    public BlobServiceClient blobServiceClient() {
        return new BlobServiceClientBuilder()
                .endpoint(endpoint)
                .buildClient();
    }
}
