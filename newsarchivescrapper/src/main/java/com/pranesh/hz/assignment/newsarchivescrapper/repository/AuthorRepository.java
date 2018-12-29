package com.pranesh.hz.assignment.newsarchivescrapper.repository;

import com.pranesh.hz.assignment.newsarchivescrapper.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ElasticsearchRepository<Author, String> {

    Page<Author> findByName(String name, Pageable pageable);
}
