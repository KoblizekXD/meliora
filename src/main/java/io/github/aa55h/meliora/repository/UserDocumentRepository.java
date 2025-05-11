package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, UUID> {
}
