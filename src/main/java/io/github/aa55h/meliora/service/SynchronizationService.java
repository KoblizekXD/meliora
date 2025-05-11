package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.config.KafkaProducerConfiguration;
import io.github.aa55h.meliora.document.AlbumDocument;
import io.github.aa55h.meliora.document.PlaylistDocument;
import io.github.aa55h.meliora.document.SongDocument;
import io.github.aa55h.meliora.document.UserDocument;
import io.github.aa55h.meliora.model.Album;
import io.github.aa55h.meliora.model.Artist;
import io.github.aa55h.meliora.model.Song;
import io.github.aa55h.meliora.repository.AlbumDocumentRepository;
import io.github.aa55h.meliora.repository.PlaylistDocumentRepository;
import io.github.aa55h.meliora.repository.SongDocumentRepository;
import io.github.aa55h.meliora.repository.UserDocumentRepository;
import io.github.aa55h.meliora.util.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public final class SynchronizationService {
    private static final Logger log = LoggerFactory.getLogger(SynchronizationService.class);
    private final UserDocumentRepository userDocumentRepository;
    private final PlaylistDocumentRepository playlistDocumentRepository;
    private final AlbumDocumentRepository albumDocumentRepository;
    private final SongDocumentRepository songDocumentRepository;

    public SynchronizationService(UserDocumentRepository userDocumentRepository, PlaylistDocumentRepository playlistDocumentRepository, AlbumDocumentRepository albumDocumentRepository, SongDocumentRepository songDocumentRepository) {
        this.userDocumentRepository = userDocumentRepository;
        this.playlistDocumentRepository = playlistDocumentRepository;
        this.albumDocumentRepository = albumDocumentRepository;
        this.songDocumentRepository = songDocumentRepository;
    }

    @KafkaListener(topics = KafkaProducerConfiguration.USER_CHANGE)
    public void synchronizeUser(@Header(KafkaHeaders.RECEIVED_KEY) String key, UserChangeEvent user) {
        log.info("[User-{}] on user {}", user.getAction(), key);
        if (user.getAction() == ChangeEvent.Action.DELETE) {
            userDocumentRepository.deleteById(UUID.fromString(key));
        } else {
            var userDocument = new UserDocument(
                    user.getEntity().getId(),
                    user.getEntity().getUsername(),
                    user.getEntity().getEmail(),
                    user.getEntity().getProfilePictureUrl()
            );
            userDocumentRepository.save(userDocument);
        }
    }
    
    @KafkaListener(topics = KafkaProducerConfiguration.PLAYLIST_CHANGE)
    public void synchronizePlaylist(@Header(KafkaHeaders.RECEIVED_KEY) String key, PlaylistChangeEvent playlist) {
        log.info("[Playlist-{}] on playlist {}", playlist.getAction(), playlist.getEntity().getId());
        if (playlist.getAction() == ChangeEvent.Action.DELETE) {
            playlistDocumentRepository.deleteById(UUID.fromString(key));
        } else {
            var playlistDocument = new PlaylistDocument(
                    playlist.getEntity().getId(),
                    playlist.getEntity().getName(),
                    playlist.getEntity().getDescription(),
                    playlist.getEntity().getSongs()
                            .stream()
                            .map(song -> new SongDocument(
                                    song.getId(),
                                    song.getTitle(),
                                    song.getArtists().stream().map(Artist::getName).collect(Collectors.toSet()), 
                                    song.getAlbums().stream().map(Album::getName).collect(Collectors.toSet())))
                            .collect(Collectors.toSet()),
                    playlist.getEntity().isPublic()
            );
            playlistDocumentRepository.save(playlistDocument);
        }
    }
    
    @KafkaListener(topics = KafkaProducerConfiguration.ALBUM_CHANGE)
    public void synchronizeAlbum(@Header(KafkaHeaders.RECEIVED_KEY) String key, AlbumChangeEvent album) {
        log.info("[Album-{}] on album {}", album.getAction(), album.getEntity().getId());
        if (album.getAction() == ChangeEvent.Action.DELETE) {
            albumDocumentRepository.deleteById(UUID.fromString(key));
        } else {
            var albumDocument = new AlbumDocument(
                    album.getEntity().getId(),
                    album.getEntity().getName(),
                    album.getEntity().getDescription(),
                    album.getEntity().getSongs()
                            .stream()
                            .map(Song::getTitle)
                            .collect(Collectors.toSet()),
                    album.getEntity().getArtists()
                            .stream()
                            .map(Artist::getName)
                            .collect(Collectors.toSet())
            );
            albumDocumentRepository.save(albumDocument);
        }
    }
    
    @KafkaListener(topics = KafkaProducerConfiguration.SONG_CHANGE)
    public void synchronizeSong(@Header(KafkaHeaders.RECEIVED_KEY) String key, SongChangeEvent song) {
        log.info("[Song-{}] on song {}", song.getAction(), key);
        if (song.getAction() == ChangeEvent.Action.DELETE) {
            songDocumentRepository.deleteById(UUID.fromString(key));
        } else {
            var songDocument = new SongDocument(
                    song.getEntity().getId(),
                    song.getEntity().getTitle(),
                    song.getEntity().getArtists()
                            .stream()
                            .map(Artist::getName)
                            .collect(Collectors.toSet()),
                    song.getEntity().getAlbums()
                            .stream()
                            .map(Album::getName)
                            .collect(Collectors.toSet())
            );
            songDocumentRepository.save(songDocument);
        }
    }
}
