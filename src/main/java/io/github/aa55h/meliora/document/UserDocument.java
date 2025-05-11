package io.github.aa55h.meliora.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Document(indexName = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument {
    @Id
    private UUID id;

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Text, index = false)
    private String profilePictureUrl;
}
