package io.github.aa55h.meliora.util.event;

import io.github.aa55h.meliora.model.Album;

public class AlbumChangeEvent extends ChangeEvent<Album> {
    public AlbumChangeEvent(Action action, Album entity) {
        super(action, entity);
    }

    public AlbumChangeEvent() {
    }
}
