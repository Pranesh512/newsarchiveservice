package com.pranesh.hz.assignment.newsarchivescrapper.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "news-archives", type = "author")
public class Author {

    @Id
    private String id;

    private String name;

    public Author(String name) {
        this.name = name;
    }
}
