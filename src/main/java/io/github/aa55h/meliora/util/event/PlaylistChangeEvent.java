package io.github.aa55h.meliora.util.event;

import io.github.aa55h.meliora.model.Playlist;

public class PlaylistChangeEvent extends ChangeEvent<Playlist> {
    public PlaylistChangeEvent(Action action, Playlist entity) {
        super(action, entity);
    }

    public PlaylistChangeEvent() {
    }
}
