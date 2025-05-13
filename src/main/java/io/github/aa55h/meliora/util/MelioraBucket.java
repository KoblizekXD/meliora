package io.github.aa55h.meliora.util;

import java.util.Locale;

/**
 * Enum representing the bucket names used in the Meliora backend.
 */
public enum MelioraBucket {
    RAW_MUSIC,
    MUSIC_METADATA,
    MUSIC_SEGMENTS,
    OTHER_METADATA, // For playlists, albums or artists
    TEST_BUCKET;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.US).replace('_', '-');
    }
}
