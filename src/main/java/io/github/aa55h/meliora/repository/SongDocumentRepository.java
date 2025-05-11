package io.github.aa55h.meliora.repository;

import io.github.aa55h.meliora.document.SongDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface SongDocumentRepository extends ElasticsearchRepository<SongDocument, UUID> {
}
