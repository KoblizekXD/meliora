package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.dto.SongUploadRequest;
import io.github.aa55h.meliora.repository.ArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MusicService {

    private final ArtistRepository artistRepository;

    public MusicService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    /**
     * @param songUploadRequest the request to the song to upload
     * @return object to respond to the client
     */
    public Object serverBeginSongProcessing(SongUploadRequest songUploadRequest, MultipartFile file) {
        return true;
    }
}
