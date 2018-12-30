package com.pranesh.hz.assignment.newsarchivescrapper.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(indexName = "news-archives", type = "_doc")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticlesArchiveDoc {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private DocType type;

    @Field(type = FieldType.Text)
    private String authorName;

    @Field(type = FieldType.Keyword)
    private String authorNameHash;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Date)
    private Date publishDate;

    public ArticlesArchiveDoc(String authorName, String authorNameHash) {
        this.authorName = authorName;
        this.authorNameHash = authorNameHash;
        this.type = DocType.AUTHOR;
    }

    public ArticlesArchiveDoc(String authorName, String title, String description, Date publishDate) {
        this.authorName = authorName;
        this.title = title;
        this.description = description;
        this.publishDate = publishDate;
        this.type = DocType.ARTICLE;
    }
}
