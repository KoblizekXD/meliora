package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.util.FileServiceException;
import io.github.aa55h.meliora.util.MelioraBucket;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * A generic file storage service which uses MinIO as the underlying storage system.
 */
@Service
public class FileStorageService {
    private final MinioClient minioClient;

    public FileStorageService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * Uploads a file to the specified bucket.
     * @throws FileServiceException if an error occurs during the operation
     * @param bucket the bucket to upload the file to
     * @param path the path to the file
     * @param multipartFile the file to upload
     */
    public void upload(MelioraBucket bucket, String path, MultipartFile multipartFile) {
        try (var stream = multipartFile.getInputStream()) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket.toString())
                    .object(path)
                    .stream(stream, stream.available(), -1)
                    .build());
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Inserts a file into the specified bucket.
     * @param bucket the bucket to upload the file to
     * @param path the path to the file
     * @param stream the InputStream of the file to upload
     */
    public void insert(MelioraBucket bucket, String path, InputStream stream) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket.toString())
                    .object(path)
                    .stream(stream, stream.available(), -1)
                    .build());
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Gets a file from the specified bucket.
     * @param bucket the bucket to get the file from
     * @param path the path to the file
     * @return the InputStream of the file, or null if the file does not exist
     */
    public InputStream get(MelioraBucket bucket, String path) {
        try {
            return minioClient.getObject(GetObjectArgs
                    .builder()
                    .bucket(bucket.toString())
                    .object(path)
                    .build());
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey"))
                return null;
            else throw new FileServiceException(e);
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Deletes a file from the specified bucket.
     * @throws FileServiceException if an error occurs during the operation
     * @param bucket the bucket to delete the file from
     * @param path the path to the file
     */
    public void delete(MelioraBucket bucket, String path) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket.toString())
                    .object(path)
                    .build());
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Ensures that a bucket exists, creating it if it does not.
     * @throws FileServiceException if an error occurs during the operation
     * @param bucket the bucket to check existence of
     */
    public void ensureBucketExistence(MelioraBucket bucket) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket.toString())
                    .build())) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket.toString())
                        .build());
            }
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Ensures that a bucket is deleted if it exists.
     * @param bucket the bucket to delete
     * @return true if the bucket was deleted, false if it did not exist
     */
    public boolean ensureDeleteBucket(MelioraBucket bucket) {
        try {
            if (minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket.toString())
                    .build())) {
                deleteBucketContents(bucket);
                minioClient.removeBucket(RemoveBucketArgs.builder()
                        .bucket(bucket.toString())
                        .build());
                return true;
            }
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
        return false;
    }
    
    public void deleteBucketContents(MelioraBucket bucket) {
        try {
            Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucket.toString())
                    .build());
            for (Result<Item> item : objects) {
                minioClient.removeObject(RemoveObjectArgs.builder()
                        .bucket(bucket.toString())
                        .object(item.get().objectName())
                        .build());
            }
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Deletes a bucket.
     * @throws FileServiceException if an error occurs during the operation
     * @param bucket the bucket to delete
     */
    public void deleteBucket(MelioraBucket bucket) {
        try {
            deleteBucketContents(bucket);
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucket.toString())
                    .build());
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Creates a new bucket.
     * @throws FileServiceException if an error occurs during the operation
     * @param bucket the bucket to create
     */
    public void createBucket(MelioraBucket bucket) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket.toString())
                    .build());
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Checks if a bucket exists.
     * @throws FileServiceException if an error occurs during the operation
     * @param bucket the bucket to check existence of
     * @return true if the bucket exists, false otherwise
     */
    public boolean bucketExists(MelioraBucket bucket) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket.toString())
                    .build());
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchBucket"))
                return false;
            else throw new FileServiceException(e);
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }

    /**
     * Checks if an object exists in the specified bucket.
     * @throws FileServiceException if an error occurs during the operation
     * @param bucket the bucket to check file existence in
     * @param path the path to the file
     * @return true if the file exists, false otherwise
     */
    public boolean objectExists(MelioraBucket bucket, String path) {
        try {
            return minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucket.toString())
                    .object(path)
                    .build()) != null;
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey"))
                return false;
            else throw new FileServiceException(e);
        } catch (Exception e) {
            throw new FileServiceException(e);
        }
    }
    
    @PostConstruct
    public void postConstruct() {
        for (MelioraBucket value : MelioraBucket.values()) {
            ensureBucketExistence(value);
        }
    }
}
