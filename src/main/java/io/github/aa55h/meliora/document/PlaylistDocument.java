package io.github.aa55h.meliora.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Document(indexName = "playlists")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDocument {
    @Id
    private UUID id;
    
    @Field(type = FieldType.Text)
    private String name;
    
    @Field(type = FieldType.Text)
    private String description;
}
