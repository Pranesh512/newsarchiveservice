package com.pranesh.hz.assignment.newsarchivescrapper.repository;

import com.pranesh.hz.assignment.newsarchivescrapper.entity.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
}
