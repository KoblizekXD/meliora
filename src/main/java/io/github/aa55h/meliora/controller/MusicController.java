package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.SongUploadRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/music")
public class MusicController {
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MODIFY_SONG')")
    public void uploadSong(@Valid @RequestPart("metadata") SongUploadRequest body,
                           @RequestPart("file") MultipartFile file) {
        
    }
}
