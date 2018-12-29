package com.pranesh.hz.assignment.newsarchivescrapper.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Parent;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(indexName = "news-archives", type = "article")
public class Article {

    @Id
    private String id;
    private String title;
    private String description;
    @Parent(type = "author")
    private String authorId;
    private Date publishDate;

    public Article(String title, String description, String authorId, Date publishDate) {
        this.title = title;
        this.description = description;
        this.authorId = authorId;
        this.publishDate = publishDate;
    }
}


