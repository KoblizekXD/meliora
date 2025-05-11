package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.document.UserDocument;
import io.github.aa55h.meliora.model.User;
import io.github.aa55h.meliora.repository.UserDocumentRepository;
import io.github.aa55h.meliora.util.ChangeEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public final class SynchronizationService {
    private final UserDocumentRepository userDocumentRepository;

    public SynchronizationService(UserDocumentRepository userDocumentRepository) {
        this.userDocumentRepository = userDocumentRepository;
    }

    @KafkaListener
    public void synchronizeUser(ChangeEvent<User> user) {
        if (user.action() == ChangeEvent.Action.DELETE) {
            userDocumentRepository.deleteById(user.entity().getId());
        } else {
            var userDocument = new UserDocument(
                    user.entity().getId(),
                    user.entity().getUsername(),
                    user.entity().getEmail(),
                    user.entity().getProfilePictureUrl()
            );
            userDocumentRepository.save(userDocument);
        }
    }
}
