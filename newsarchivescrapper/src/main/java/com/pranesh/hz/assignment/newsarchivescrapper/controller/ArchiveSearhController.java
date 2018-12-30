package com.pranesh.hz.assignment.newsarchivescrapper.controller;

import com.pranesh.hz.assignment.newsarchivescrapper.model.ScrappedArticle;
import com.pranesh.hz.assignment.newsarchivescrapper.model.SearchResponse;
import com.pranesh.hz.assignment.newsarchivescrapper.service.ArticleArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class ArchiveSearhController {

    @Autowired
    private ArticleArchiveService service;

    @GetMapping(value = "authors", produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchResponse<String> searchAuthors(@RequestParam String q) {
        List<String> authors = service.searchAuthors(q);
        return new SearchResponse<>(authors);
    }

    @GetMapping(value = "authors/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchResponse<ScrappedArticle> searchArticlesByAuthor(@RequestParam String q) {
        List<ScrappedArticle> articles = service.searchArticlesByAuthor(q);
        return new SearchResponse<>(articles);
    }

    @GetMapping(value = "articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public SearchResponse<ScrappedArticle> searchArticles(@RequestParam String q) {
        List<ScrappedArticle> articles = service.searchArticlesByTitleAndDesc(q);
        return new SearchResponse<>(articles);
    }
}
