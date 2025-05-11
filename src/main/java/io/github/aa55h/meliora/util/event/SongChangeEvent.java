package io.github.aa55h.meliora.util.event;

import io.github.aa55h.meliora.model.Song;

public class SongChangeEvent extends ChangeEvent<Song> {
    public SongChangeEvent(Action action, Song entity) {
        super(action, entity);
    }

    public SongChangeEvent() {
    }
}
