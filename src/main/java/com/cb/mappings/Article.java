package com.cb.mappings;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "blog")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Text, name = "content")
    private String content;

    @Field(type = FieldType.Text, name = "category_id")
    @JsonAlias("category_id")
    private String categoryId;

    @Field(type = FieldType.Text, name = "author_id")
    @JsonAlias("author_id")
    private String authorId;

    @Field(type = FieldType.Text, name = "url", index = false)
    private String url;

    @JsonIgnore
    private transient String _class;

    public Article(String id) {
        this.id = id;
    }
}
