package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.GenericErrorResponse;
import io.github.aa55h.meliora.dto.SongResponse;
import io.github.aa55h.meliora.dto.SongUploadRequest;
import io.github.aa55h.meliora.model.Song;
import io.github.aa55h.meliora.service.MusicService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {
    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
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
}
