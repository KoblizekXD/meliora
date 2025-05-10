package io.github.aa55h.meliora.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    
    @Value("${MINIO_ROOT_USER}")
    private String minioUsername;

    @Value("${MINIO_ROOT_PASSWORD}")
    private String minioPassword;
    
    @Value("${MINIO_URL}")
    private String minioUrl;
    
    @Bean
    public MinioClient minioClient() {
        String url = minioUrl;
        if (url == null || url.isEmpty()) {
            minioUrl = "localhost";
        }
        return MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioUsername, minioPassword)
                .build();
    }
}
