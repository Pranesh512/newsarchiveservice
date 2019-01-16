package com.pranesh.samples.newsarchivescrapper.model;

import lombok.Data;

import java.util.Date;

@Data
public class ScrappedArticle {

    private final String title;
    private final String description;
    private final String authorName;
    private final Date publishDate;
}
