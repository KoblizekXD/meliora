package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.document.ArtistDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface ArtistDocumentRepository extends ElasticsearchRepository<ArtistDocument, UUID> {
}
