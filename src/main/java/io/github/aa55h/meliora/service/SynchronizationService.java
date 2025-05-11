package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.config.KafkaProducerConfiguration;
import io.github.aa55h.meliora.document.UserDocument;
import io.github.aa55h.meliora.repository.UserDocumentRepository;
import io.github.aa55h.meliora.util.event.ChangeEvent;
import io.github.aa55h.meliora.util.event.UserChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public final class SynchronizationService {
    private static final Logger log = LoggerFactory.getLogger(SynchronizationService.class);
    private final UserDocumentRepository userDocumentRepository;

    public SynchronizationService(UserDocumentRepository userDocumentRepository) {
        this.userDocumentRepository = userDocumentRepository;
    }

    @KafkaListener(topics = KafkaProducerConfiguration.USER_CHANGE)
    public void synchronizeUser(UserChangeEvent user) {
        log.info("[User-{}] on user {}", user.getAction(), user.getEntity().getId());
        if (user.getAction() == ChangeEvent.Action.DELETE) {
            userDocumentRepository.deleteById(user.getEntity().getId());
        } else {
            var userDocument = new UserDocument(
                    user.getEntity().getId(),
                    user.getEntity().getUsername(),
                    user.getEntity().getEmail(),
                    user.getEntity().getProfilePictureUrl()
            );
            userDocumentRepository.save(userDocument);
        }
    }
}
