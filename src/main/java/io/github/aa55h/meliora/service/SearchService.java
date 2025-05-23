package io.github.aa55h.meliora.service;

import io.github.aa55h.meliora.document.UserDocument;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public final class SearchService {

    private final ElasticsearchOperations elasticsearchOperations;

    public SearchService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }
    
    public Set<UserDocument> searchUsers(String term) {
        return Set.of();
    }
}
