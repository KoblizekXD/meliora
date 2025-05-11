package io.github.aa55h.meliora.util;

public record ChangeEvent<T>(
        Action action,
        T entity
) {
    public enum Action {
        CREATE,
        UPDATE,
        DELETE
    }
}
