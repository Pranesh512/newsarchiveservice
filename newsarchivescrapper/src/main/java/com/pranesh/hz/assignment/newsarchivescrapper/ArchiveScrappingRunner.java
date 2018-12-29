package com.pranesh.hz.assignment.newsarchivescrapper;

import com.pranesh.hz.assignment.newsarchivescrapper.service.ArticleArchiveService;
import org.elasticsearch.action.get.GetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchiveScrappingRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArchiveScrappingRunner.class);

    @Autowired
    private ArticleArchiveService archiveService;

    @Autowired
    private ElasticsearchTemplate esTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Wait until elasticsearch server starts
        boolean connected = false;
        while (!connected) {
            try {
                esTemplate.getClient().get(new GetRequest("_doc"));
                connected = true;
            } catch (Exception e) {
                LOGGER.info("Waiting for elasticsearch to start");
                Thread.sleep(10000);
            }
        }
        archiveService.runScrapping();
    }
}
