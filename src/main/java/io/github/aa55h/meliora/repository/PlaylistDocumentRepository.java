package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.document.PlaylistDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface PlaylistDocumentRepository extends ElasticsearchRepository<PlaylistDocument, UUID> {
}
