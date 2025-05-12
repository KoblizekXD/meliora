package io.github.aa55h.meliora.util;

import java.util.UUID;

public final class UUIDParser {
    private UUIDParser() {}

    /**
     * Safety-net UUID parser.
     * This method attempts to parse a UUID string and throws a custom exception if the format is invalid.
     * @param uuid UUID string
     * @return UUID object
     */
    public static UUID tryParse(String uuid) {
        if (uuid == null) {
            throw new UUIDParseException("UUID cannot be null");
        }
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new UUIDParseException("Invalid UUID format: " + uuid);
        }
    }
    
    public static class UUIDParseException extends RuntimeException {
        public UUIDParseException(String message) {
            super(message);
        }
    }
}
