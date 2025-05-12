package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.config.KafkaProducerConfiguration;
import io.github.aa55h.meliora.dto.SongUploadRequest;
import io.github.aa55h.meliora.model.Song;
import io.github.aa55h.meliora.repository.AlbumRepository;
import io.github.aa55h.meliora.repository.ArtistRepository;
import io.github.aa55h.meliora.repository.GenreRepository;
import io.github.aa55h.meliora.repository.SongRepository;
import io.github.aa55h.meliora.util.MelioraBucket;
import io.github.aa55h.meliora.util.event.ChangeEvent;
import io.github.aa55h.meliora.util.event.SongChangeEvent;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * Service responsible for managing music-related operations(uploading, removing, edits etc.).
 */
@Service
public class MusicService {

    private static final Logger log = LoggerFactory.getLogger(MusicService.class);
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final SongRepository songRepository;
    private final FileStorageService fileStorageService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final FFMpegService fFMpegService;

    public MusicService(ArtistRepository artistRepository, AlbumRepository albumRepository, GenreRepository genreRepository, SongRepository songRepository, FileStorageService fileStorageService, KafkaTemplate<String, Object> kafkaTemplate, FFMpegService fFMpegService) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.genreRepository = genreRepository;
        this.songRepository = songRepository;
        this.fileStorageService = fileStorageService;
        this.kafkaTemplate = kafkaTemplate;
        this.fFMpegService = fFMpegService;
    }
    
    @Transactional
    public Song serverBeginSongProcessing(SongUploadRequest songUploadRequest, MultipartFile file) {
        var song = new Song();
        song.setTitle(songUploadRequest.title());
        song.setDuration(-1L);
        song.setFinishedProcessing(false);
        song.setArtists(new HashSet<>(artistRepository.findAllById(songUploadRequest.artists())));
        song.setAlbums(new HashSet<>(albumRepository.findAllById(songUploadRequest.albums())));
        song.setGenres(new HashSet<>(genreRepository.findAllById(songUploadRequest.genres())));
        song = songRepository.save(song);
        uploadRawSongFile(song.getId(), file);
        kafkaTemplate.send(KafkaProducerConfiguration.SONG_UPLOAD,
                song.getId().toString(), song);
        return song;
    }
    
    public void uploadRawSongFile(UUID song, MultipartFile file) {
        fileStorageService.upload(MelioraBucket.RAW_MUSIC, song.toString() + ".mp3", file);
    }
    
    @KafkaListener(topics = KafkaProducerConfiguration.SONG_UPLOAD)
    @Transactional
    public void processSongUpload(Song song) {
        song.setFinishedProcessing(true);
        Optional<FFMpegService.M3U8> m3u8 = fFMpegService.generateM3U8(song.getId());
        if (m3u8.isPresent()) {
            try {
                FFMpegService.M3U8 m3u8Obj = m3u8.get();
                for (int i = 0; i < m3u8Obj.segments().length; i++) {
                    fileStorageService.insert(MelioraBucket.MUSIC_SEGMENTS, song.getId() + "/segment_" + i + ".aac", m3u8Obj.segments()[i]);
                }
                fileStorageService.insert(MelioraBucket.MUSIC_METADATA, song.getId() + "/playlist.m3u8", m3u8Obj.metadata());
                m3u8Obj.metadata().close();
                for (var segment : m3u8Obj.segments()) {
                    segment.close();
                }
                FileSystemUtils.deleteRecursively(m3u8Obj.tempDir());
                songRepository.save(song);
                kafkaTemplate.send(KafkaProducerConfiguration.SONG_CHANGE,
                        song.getId().toString(), new SongChangeEvent(ChangeEvent.Action.CREATE, song));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("Failed to generate M3U8 for song {}", song.getId());
        }
    }
    
    public InputStream getRawSongFile(UUID song) {
        return fileStorageService.get(MelioraBucket.RAW_MUSIC, song.toString() + ".mp3");
    }
    
    public InputStream getSegment(UUID song, int segment) {
        return fileStorageService.get(MelioraBucket.MUSIC_SEGMENTS, song + "/segment_" + segment + ".aac");
    }
    
    public InputStream getPlaylist(UUID song) {
        return fileStorageService.get(MelioraBucket.MUSIC_METADATA, song + "/playlist.m3u8");
    }
}
