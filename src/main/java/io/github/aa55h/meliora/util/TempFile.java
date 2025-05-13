package io.github.aa55h.meliora.util;

import lombok.Getter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class TempFile extends InputStream implements Closeable {

    @Getter
    private final Path path;
    private final InputStream inputStream;

    private TempFile(Path path, boolean createIs) throws IOException {
        this.path = path;
        this.inputStream = createIs ? Files.newInputStream(path) : null;
    }
    
    public static TempFile create() throws IOException {
        return new TempFile(Files.createTempFile("meliora-ffprobe-", null), true);
    }
    
    public static TempFile create(InputStream data) throws IOException {
        Path tempFile = Files.createTempFile("meliora-ffprobe-", null);
        Files.copy(data, tempFile, StandardCopyOption.REPLACE_EXISTING);
        data.close();
        return new TempFile(tempFile, false);
    }
    
    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
        }
        Files.deleteIfExists(path);
    }
}
