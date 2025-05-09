package io.github.aa55h.meliora.util;

import java.util.Locale;

/**
 * Enum representing the bucket names used in the Meliora backend.
 */
public enum MelioraBucket {
    ;

    @Override
    public String toString() {
        return name().toLowerCase(Locale.US).replace('_', '-');
    }
}
