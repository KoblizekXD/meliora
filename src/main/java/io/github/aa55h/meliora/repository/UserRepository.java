package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UUID, User> {
}
