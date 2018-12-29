package com.pranesh.hz.assignment.newsarchivescrapper.service;

import com.pranesh.hz.assignment.newsarchivescrapper.entity.Article;
import com.pranesh.hz.assignment.newsarchivescrapper.entity.Author;
import com.pranesh.hz.assignment.newsarchivescrapper.model.ScrappedArticle;
import com.pranesh.hz.assignment.newsarchivescrapper.repository.ArticleRepository;
import com.pranesh.hz.assignment.newsarchivescrapper.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class ArticleArchiveService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private List<INewsArchiveScrapperService> scrapperServices;

    public void runScrapping() {
        scrapperServices.forEach(scrapper -> {
            List<ScrappedArticle> scrappedArticles = scrapper.doScrap();
            scrappedArticles.forEach(sa -> {
                Author author;
                Page<Author> authors = authorRepository.findByName(sa.getAuthorName(), PageRequest.of(0, 1));
                if (!authors.isEmpty()) {
                    author = authors.getContent().get(0);
                } else {
                    author = new Author(sa.getAuthorName());
                    authorRepository.save(author);
                }
                Article article = new Article(sa.getTitle(), sa.getDescription(), author.getId(), sa.getPublishDate());
                articleRepository.save(article);
            });
        });
    }

    public List<String> searchAuthors(String authorNameQuery) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("name", authorNameQuery).minimumShouldMatch("75%"))
                .build();
        Page<Author> authors = authorRepository.search(searchQuery);
        return authors.get().map(Author::getName).collect(Collectors.toList());
    }


}
