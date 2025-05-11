package io.github.aa55h.meliora.util.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChangeEvent<T> {
    private Action action;
    private T entity;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ChangeEvent) obj;
        return Objects.equals(this.action, that.action) &&
                Objects.equals(this.entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, entity);
    }

    @Override
    public String toString() {
        return "ChangeEvent[" +
                "action=" + action + ", " +
                "entity=" + entity + ']';
    }

    public enum Action {
        CREATE,
        UPDATE,
        DELETE
    }
}
