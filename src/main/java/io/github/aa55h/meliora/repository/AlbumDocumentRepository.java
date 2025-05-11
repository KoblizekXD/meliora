package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.document.AlbumDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface AlbumDocumentRepository extends ElasticsearchRepository<AlbumDocument, UUID> {
}
