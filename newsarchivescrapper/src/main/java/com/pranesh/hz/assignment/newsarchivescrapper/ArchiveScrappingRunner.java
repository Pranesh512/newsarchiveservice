package com.pranesh.hz.assignment.newsarchivescrapper;

import com.pranesh.hz.assignment.newsarchivescrapper.service.ArticleArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ArchiveScrappingRunner implements ApplicationRunner {

    @Autowired
    private ArticleArchiveService archiveService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        archiveService.runScrapping();
    }
}
