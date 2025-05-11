package io.github.aa55h.meliora.util.event;

import io.github.aa55h.meliora.model.User;

public class UserChangeEvent extends ChangeEvent<User> {
    public UserChangeEvent(Action action, User entity) {
        super(action, entity);
    }

    public UserChangeEvent() {
    }
}
