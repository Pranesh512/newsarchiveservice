package com.pranesh.hz.assignment.newsarchivescrapper.service;

import com.pranesh.hz.assignment.newsarchivescrapper.model.ScrappedArticle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleArchiveServiceTest {

    @Autowired
    private ArticleArchiveService archiveService;

    @Test
    public void searchAuthors() {
        List<String> results = archiveService.searchAuthors("Ashley");
        System.out.println("results = " + results);
        assertTrue(!results.isEmpty());
        assertTrue(results.get(0).toLowerCase().contains("ashley"));
    }

    @Test
    public void searchArticlesByAuthor() {
        List<ScrappedArticle> results = archiveService.searchArticlesByAuthor("Ashley");
        System.out.println("results = " + results);
        assertTrue(!results.isEmpty());
        assertTrue(results.get(0).getAuthorName().toLowerCase().contains("ashley"));
    }

    @Test
    public void searchArticlesByTitleAndDesc() {
        List<ScrappedArticle> results = archiveService.searchArticlesByTitleAndDesc("court");
        System.out.println("results = " + results);
        assertTrue(!results.isEmpty());
        ScrappedArticle article = results.get(0);
        assertTrue(article.getTitle().contains("court") || article.getDescription().contains("court"));
    }
}