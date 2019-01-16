package com.pranesh.samples.newsarchivescrapper;

import com.pranesh.samples.newsarchivescrapper.service.ArticleArchiveService;
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
                esTemplate.getClient().admin().cluster().prepareHealth().setWaitForYellowStatus().get();
                connected = true;
            } catch (Exception e) {
                LOGGER.info("Waiting for elasticsearch to start");
                Thread.sleep(10000);
            }
        }
        LOGGER.info("elasticsearch connected");
        archiveService.runScrapping();
    }
}
