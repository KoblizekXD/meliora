package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.GenericErrorResponse;
import io.github.aa55h.meliora.dto.SongResponse;
import io.github.aa55h.meliora.dto.SongUploadRequest;
import io.github.aa55h.meliora.model.Song;
import io.github.aa55h.meliora.repository.SongRepository;
import io.github.aa55h.meliora.service.MusicService;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tracks")
public class TrackController {
    private final MusicService musicService;
    private final SongRepository songRepository;

    public TrackController(MusicService musicService, SongRepository songRepository) {
        this.musicService = musicService;
        this.songRepository = songRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MODIFY_SONG')")
    public ResponseEntity<Object> uploadSong(@Valid @RequestPart("metadata") SongUploadRequest body,
                                     @RequestPart("file") MultipartFile file) {
        if (!"audio/mpeg".equals(file.getContentType())) {
            return ResponseEntity.badRequest()
                    .body(new GenericErrorResponse(
                            "Invalid file type. Only MP3 files are allowed.",
                            "/api/v1/music",
                            400,
                            System.currentTimeMillis()
                    ));
        }
        Song song = musicService.serverBeginSongProcessing(body, file);
        return ResponseEntity.ok(new SongResponse(
                song.getTitle(),
                song.getId(),
                "/api/v1/stream/" + song.getId(),
                "/api/v1/tracks/" + song.getId()
        ));
    }
    
    @PreAuthorize("hasAuthority('DOWNLOAD_SONG')")
    @PostMapping("/{id}/raw")
    public ResponseEntity<Object> getRawSong(@PathVariable UUID id) {
        Optional<InputStream> rawSongFile = musicService.getRawSongFile(id);
        return rawSongFile.<ResponseEntity<Object>>map(inputStream -> ResponseEntity.ok(new InputStreamResource(inputStream))).orElseGet(() -> new ResponseEntity<>(new GenericErrorResponse(
                "Track not found",
                "/api/v1/tracks/" + id + "/raw",
                404,
                System.currentTimeMillis()
        ), HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<Object> getSong(@PathVariable UUID id) {
        Optional<Song> byId = songRepository.findById(id);
        if (byId.isPresent()) {
            Song song = byId.get();
            return ResponseEntity.ok(new SongResponse(
                    song.getTitle(),
                    song.getId(),
                    "/api/v1/stream/" + song.getId(),
                    "/api/v1/tracks/" + song.getId()
            ));
        } else {
            return new ResponseEntity<>(new GenericErrorResponse(
                    "Track not found",
                    "/api/v1/tracks/" + id,
                    404,
                    System.currentTimeMillis()
            ), HttpStatus.NOT_FOUND);
        }
    }
}
