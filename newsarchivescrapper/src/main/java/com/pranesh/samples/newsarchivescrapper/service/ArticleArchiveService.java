package com.pranesh.samples.newsarchivescrapper.service;

import com.pranesh.samples.newsarchivescrapper.entity.ArticlesArchiveDoc;
import com.pranesh.samples.newsarchivescrapper.model.ScrappedArticle;
import com.pranesh.samples.newsarchivescrapper.repository.ArticlesArchiveESRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleArchiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleArchiveService.class);

    @Autowired
    private ArticlesArchiveESRepository esRepository;

    @Autowired
    private List<INewsArchiveScrapperService> scrapperServices;

    public void runScrapping() {
        long count = esRepository.count();
        if (count > 0) {
            LOGGER.info("Scrapping already done");
            LOGGER.debug("Scrapped doc count=[" + count + "]");
            return;
        }
        scrapperServices.forEach(scrapper -> {
            scrapper.doScrap(sa -> {
                // Hash is modified cause as negative integer hash value is taken as "not" in elastic query
                String authorNameHash = "an" + String.valueOf(sa.getAuthorName().hashCode());
                ArticlesArchiveDoc author = esRepository.findByAuthorNameHash(authorNameHash);
                if (author == null) {
                    author = new ArticlesArchiveDoc(sa.getAuthorName(), authorNameHash);
                    esRepository.save(author);
                }
                ArticlesArchiveDoc article = new ArticlesArchiveDoc(sa.getAuthorName(), sa.getTitle(),
                        sa.getDescription(), sa.getPublishDate());
                esRepository.save(article);
            });
        });
    }

    public List<String> searchAuthors(String authorNameQuery) {
        List<ArticlesArchiveDoc> authors = esRepository.searchAuthors(authorNameQuery);
        return authors.stream().map(ArticlesArchiveDoc::getAuthorName).collect(Collectors.toList());
    }

    public List<ScrappedArticle> searchArticlesByAuthor(String authorNameQuery) {
        List<ArticlesArchiveDoc> articles = esRepository.searchArticlesByAuthor(authorNameQuery);
        return transformToModel(articles);
    }

    public List<ScrappedArticle> searchArticlesByTitleAndDesc(String query) {
        List<ArticlesArchiveDoc> articles = esRepository.searchArticlesByTitleAndDesc(query);
        return transformToModel(articles);
    }

    private List<ScrappedArticle> transformToModel(List<ArticlesArchiveDoc> articles) {
        return articles.stream()
                .map(doc -> new ScrappedArticle(
                        doc.getTitle(), doc.getDescription(), doc.getAuthorName(), doc.getPublishDate()))
                .collect(Collectors.toList());
    }
}
