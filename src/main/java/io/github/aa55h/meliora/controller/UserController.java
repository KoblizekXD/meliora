package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.PublicPlaylistData;
import io.github.aa55h.meliora.dto.PublicUserResponse;
import io.github.aa55h.meliora.model.Playlist;
import io.github.aa55h.meliora.model.Song;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.PlaylistRepository;
import io.github.aa55h.meliora.repository.UserRepository;
import io.github.aa55h.meliora.util.UUIDParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;

    public UserController(UserRepository userRepository, PlaylistRepository playlistRepository) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
    }

    @GetMapping("/@me")
    public ResponseEntity<PublicUserResponse> getSelf(Authentication authentication) {
        return new ResponseEntity<>(((User) authentication.getPrincipal()).getPublicUserResponse(), HttpStatus.OK);
    }
    
    @GetMapping("/@me/playlists")
    public ResponseEntity<Set<UUID>> getSelfPlaylists(Authentication authentication) {
        return new ResponseEntity<>(
                ((User) authentication.getPrincipal())
                        .getPlaylists().stream().map(Playlist::getId).collect(Collectors.toSet()),
                HttpStatus.OK
        );
    }
    
    @GetMapping("/@me/playlists/{playlist_id}")
    public ResponseEntity<PublicPlaylistData> getSelfPlaylist(@PathVariable("playlist_id") String playlistId, 
                                                               Authentication authentication) {
        UUID pId = UUIDParser.tryParse(playlistId);
        return playlistRepository.findByUserIdAndId(((User) authentication.getPrincipal()).getId(), pId)
                .map(it -> ResponseEntity.ok(new PublicPlaylistData(
                        it.getId(),
                        it.getName(),
                        it.getDescription(),
                        it.getCoverImageUrl(),
                        it.getSongs().stream().map(Song::getId).collect(Collectors.toSet()),
                        it.getUser().getId()
                ))).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PublicUserResponse> getUser(@PathVariable String id) {
        UUID uuid = UUIDParser.tryParse(id);
        return userRepository.findById(uuid)
                .map(user -> ResponseEntity.ok(user.getPublicUserResponse()))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/playlists")
    public ResponseEntity<Set<UUID>> getPlaylistInformation(@PathVariable String id) {
        UUID uuid = UUIDParser.tryParse(id);
        return ResponseEntity.ok(playlistRepository.findByUserIdAndPublic(true, uuid));
    }
    
    @GetMapping("/{id}/playlists/{playlist_id}")
    public ResponseEntity<PublicPlaylistData> getPlaylistInformation(@PathVariable String id, 
                                                            @PathVariable("playlist_id") String playlistId) {
        UUID uuid = UUIDParser.tryParse(id);
        UUID pId = UUIDParser.tryParse(playlistId);
        return playlistRepository.findByUserIdAndId(uuid, pId).map(it -> ResponseEntity.ok(new PublicPlaylistData(
                it.getId(),
                it.getName(),
                it.getDescription(),
                it.getCoverImageUrl(),
                it.getSongs().stream().map(Song::getId).collect(Collectors.toSet()),
                it.getUser().getId()
        ))).orElse(ResponseEntity.notFound().build());
    }
}
