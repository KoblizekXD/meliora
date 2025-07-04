package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.util.MelioraBucket;
import io.github.aa55h.meliora.util.TempFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class FFMpegService {

    private static final Logger log = LoggerFactory.getLogger(FFMpegService.class);
    private final FileStorageService fileStorageService;

    public FFMpegService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Generates an M3U8 playlist and segments for the given UUID.
     * The raw audio file is resolved from the {@link io.github.aa55h.meliora.util.MelioraBucket::RAW_MUSIC} bucket.
     * 
     * @see io.github.aa55h.meliora.util.MelioraBucket.RAW_MUSIC
     * @param uuid the UUID of the audio file(song)
     * @return an M3U8 object containing the metadata and segments as InputStreams
     */
    public Optional<M3U8> generateM3U8(UUID uuid) {
        try (InputStream raw = fileStorageService.get(MelioraBucket.RAW_MUSIC, uuid + ".mp3")) {
            if (raw == null) return Optional.empty();
            Path tempDir = Files.createTempDirectory("amused-processor-");
            Files.createDirectories(tempDir.resolve("segments"));
            ProcessBuilder pb = new ProcessBuilder(
                    "ffmpeg",
                    "-i", Files.write(tempDir.resolve(uuid + ".mp3"), raw.readAllBytes()).toString(),
                    "-codec:a", "aac",
                    "-b:a", "128k",
                    "-hls_time", "10",
                    "-hls_segment_type", "mpegts",
                    "-hls_playlist_type", "vod",
                    "-hls_segment_filename", tempDir.resolve("segments", "segment_%03d.aac").toString(),
                    tempDir.resolve("playlist.m3u8").toString());
            Process process = pb.start();
            process.waitFor();
            return Optional.of(new M3U8(
                    tempDir,
                    Files.newInputStream(tempDir.resolve("playlist.m3u8")),
                    Files.list(tempDir.resolve("segments")).map(it -> {
                                try {
                                    return Files.newInputStream(it);
                                } catch (IOException e) {
                                    log.error("Error reading segment file", e);
                                    return null;
                                }
                            })
                            .filter(Objects::nonNull)
                            .toArray(InputStream[]::new)
            ));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    public long getSongDuration(InputStream audioStream) {
        try (var tempFile = TempFile.create(audioStream)) {
            ProcessBuilder builder = new ProcessBuilder(
                    "ffprobe",
                    "-f", "mp3",
                    "-i", tempFile.getPath().toString(),
                    "-show_entries", "format=duration",
                    "-v", "quiet",
                    "-of", "csv=p=0"
            );
            Process process = builder.start();
            process.waitFor();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();

            if (line != null) {
                double seconds = Double.parseDouble(line.trim());
                return Math.round(seconds * 1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return -1;
    }
    
    public record M3U8(Path tempDir, InputStream metadata, InputStream... segments)
        implements Closeable {
        @Override
        public void close() throws IOException {
            metadata.close();
            for (InputStream segment : segments) {
                segment.close();
            }
            FileSystemUtils.deleteRecursively(tempDir);
        }
    }
}
