package io.github.aa55h.meliora.test;

import io.github.aa55h.meliora.config.MinioConfiguration;
import io.github.aa55h.meliora.service.FileStorageService;
import io.github.aa55h.meliora.util.MelioraBucket;
import io.minio.MinioClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = MinioConfiguration.class)
@ExtendWith(SpringExtension.class)
class FileStorageServiceTests {
    private static final MinIOContainer minio = new MinIOContainer("minio/minio:latest");

    @Autowired
    private MinioClient minioClient;
    private FileStorageService storageService;

    @DynamicPropertySource
    static void propertySource(DynamicPropertyRegistry registry) {
        minio.start();
        registry.add("MINIO_URL", minio::getS3URL);
        registry.add("MINIO_ROOT_USER", minio::getUserName);
        registry.add("MINIO_ROOT_PASSWORD", minio::getPassword);
    }

    @BeforeEach
    void setUp() {
        storageService = new FileStorageService(minioClient);
    }
    
    @AfterEach
    void tearDown() {
        storageService.ensureDeleteBucket(MelioraBucket.TEST_BUCKET);
    }
    
    @Test
    void testBucketExistence() {
        storageService.createBucket(MelioraBucket.TEST_BUCKET);
        assertThat(storageService.bucketExists(MelioraBucket.TEST_BUCKET)).isTrue();
    }
    
    @Test
    void testEnsureBucketExistence() {
        storageService.ensureBucketExistence(MelioraBucket.TEST_BUCKET);
        assertThat(storageService.bucketExists(MelioraBucket.TEST_BUCKET)).isTrue();
    }
    
    @Test
    void testDeleteBucket() {
        storageService.createBucket(MelioraBucket.TEST_BUCKET);
        storageService.deleteBucket(MelioraBucket.TEST_BUCKET);
        assertThat(storageService.bucketExists(MelioraBucket.TEST_BUCKET)).isFalse();
    }
    
    @Test
    void testEnsureDeleteBucket() {
        storageService.createBucket(MelioraBucket.TEST_BUCKET);
        assertThat(storageService.ensureDeleteBucket(MelioraBucket.TEST_BUCKET)).isTrue();
        assertThat(storageService.bucketExists(MelioraBucket.TEST_BUCKET)).isFalse();
    }
    
    @Test
    void testUploadAndGetFile() throws IOException {
        storageService.createBucket(MelioraBucket.TEST_BUCKET);
        String filePath = "test.txt";
        String fileContent = "Hello, World!";
        storageService.upload(MelioraBucket.TEST_BUCKET, filePath, new MockMultipartFile("file", fileContent.getBytes()));
        
        InputStream inputStream = storageService.get(MelioraBucket.TEST_BUCKET, filePath);
        assertThat(inputStream).isNotNull();
        
        String content = new String(inputStream.readAllBytes());
        assertThat(content).isEqualTo(fileContent);
    }
    
    @Test
    void testDeleteFile() {
        storageService.createBucket(MelioraBucket.TEST_BUCKET);
        String filePath = "test.txt";
        String fileContent = "Hello, World!";
        storageService.upload(MelioraBucket.TEST_BUCKET, filePath, new MockMultipartFile("file", fileContent.getBytes()));
        
        storageService.delete(MelioraBucket.TEST_BUCKET, filePath);
        
        InputStream inputStream = storageService.get(MelioraBucket.TEST_BUCKET, filePath);
        assertThat(inputStream).isNull();
    }
    
    @Test
    void testObjectExists() {
        storageService.createBucket(MelioraBucket.TEST_BUCKET);
        String filePath = "test.txt";
        String fileContent = "Hello, World!";
        storageService.upload(MelioraBucket.TEST_BUCKET, filePath, new MockMultipartFile("file", fileContent.getBytes()));
        
        assertThat(storageService.objectExists(MelioraBucket.TEST_BUCKET, filePath)).isTrue();
        
        storageService.delete(MelioraBucket.TEST_BUCKET, filePath);
        assertThat(storageService.objectExists(MelioraBucket.TEST_BUCKET, filePath)).isFalse();
    }
    
    @Test
    void testDeleteBucketContents() {
        storageService.createBucket(MelioraBucket.TEST_BUCKET);
        String filePath1 = "test1.txt";
        String filePath2 = "test2.txt";
        String fileContent = "Hello, World!";
        storageService.upload(MelioraBucket.TEST_BUCKET, filePath1, new MockMultipartFile("file", fileContent.getBytes()));
        storageService.upload(MelioraBucket.TEST_BUCKET, filePath2, new MockMultipartFile("file", fileContent.getBytes()));
        
        storageService.deleteBucketContents(MelioraBucket.TEST_BUCKET);
        
        assertThat(storageService.objectExists(MelioraBucket.TEST_BUCKET, filePath1)).isFalse();
        assertThat(storageService.objectExists(MelioraBucket.TEST_BUCKET, filePath2)).isFalse();
    }
}
