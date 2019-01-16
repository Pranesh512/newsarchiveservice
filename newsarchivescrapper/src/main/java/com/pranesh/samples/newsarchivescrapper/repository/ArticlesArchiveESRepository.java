package com.pranesh.samples.newsarchivescrapper.repository;

import com.pranesh.samples.newsarchivescrapper.entity.ArticlesArchiveDoc;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticlesArchiveESRepository extends ElasticsearchRepository<ArticlesArchiveDoc, String> {

    ArticlesArchiveDoc findByAuthorNameHash(String authorNameHash);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"type\":\"AUTHOR\"}},{\"match\":{\"authorName\":\"?0\"}}]}}")
    List<ArticlesArchiveDoc> searchAuthors(String authorNameQuery);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"type\":\"ARTICLE\"}},{\"match\":{\"authorName\":\"?0\"}}]}}")
    List<ArticlesArchiveDoc> searchArticlesByAuthor(String authorNameQuery);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"type\":\"ARTICLE\"}},{\"multi_match\":{\"query\":\"?0\",\"fields\":[\"title\",\"description\"]}}]}}")
    List<ArticlesArchiveDoc> searchArticlesByTitleAndDesc(String query);
}
