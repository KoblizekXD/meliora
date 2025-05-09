package io.github.aa55h.meliora.util;

import io.github.aa55h.meliora.service.FileStorageService;

/**
 * A custom exception for handling a file service({@link FileStorageService})-related exceptions.
 * This exception can be thrown essentially during any MinIO operation and will be caught and
 * potentially handled by proper exception handling mechanisms.
 */
public class FileServiceException extends RuntimeException {
    public FileServiceException(Throwable cause) {
        super(cause);
    }
}
