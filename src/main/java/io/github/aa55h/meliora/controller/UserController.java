package io.github.aa55h.meliora.controller;

import io.github.aa55h.meliora.dto.CreatePlaylistRequest;
import io.github.aa55h.meliora.dto.PublicPlaylistData;
import io.github.aa55h.meliora.dto.PublicUserResponse;
import io.github.aa55h.meliora.dto.UpdatePlaylistRequest;
import io.github.aa55h.meliora.model.Playlist;
import io.github.aa55h.meliora.model.Song;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.PlaylistRepository;
import io.github.aa55h.meliora.repository.SongRepository;
import io.github.aa55h.meliora.repository.UserRepository;
import io.github.aa55h.meliora.util.UUIDParser;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;

    public UserController(UserRepository userRepository, PlaylistRepository playlistRepository, SongRepository songRepository) {
        this.userRepository = userRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
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
    
    @PostMapping("/@me/playlists")
    @PreAuthorize("hasAuthority('MODIFY_PLAYLIST_SELF')")
    public ResponseEntity<?> createPlaylist(@RequestBody @Valid CreatePlaylistRequest request, Authentication authentication) {
        Playlist playlist = new Playlist();
        playlist.setName(request.name());
        playlist.setDescription(request.description());
        playlist.setCoverImageUrl(request.coverImage());
        playlist.setSongs(new HashSet<>(songRepository.findAllById(request.songs())));
        playlist.setPublic(false);
        playlist.setUser((User) authentication.getPrincipal());
        
        return ResponseEntity.ok(playlistRepository.save(playlist).asPublicPlaylistData());
    }
    
    @PutMapping("/@me/playlists/{playlist_id}")
    @PreAuthorize("hasAuthority('MODIFY_PLAYLIST_SELF')")
    public ResponseEntity<PublicPlaylistData> updatePlaylist(@PathVariable("playlist_id") String playlistId,
                                                             @RequestBody @Valid UpdatePlaylistRequest request,
                                                             Authentication authentication) {
        UUID pId = UUIDParser.tryParse(playlistId);
        return playlistRepository.findByUserIdAndId(((User) authentication.getPrincipal()).getId(), pId)
                .map(it -> {
                    it.setName(request.name());
                    it.setDescription(request.description());
                    it.getSongs().removeIf(s -> request.removed().contains(s.getId()));
                    it.getSongs().addAll(songRepository.findAllById(request.added()));
                    return ResponseEntity.ok(playlistRepository.save(it).asPublicPlaylistData());
                }).orElse(ResponseEntity.notFound().build());
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
