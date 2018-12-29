package com.pranesh.hz.assignment.newsarchivescrapper.service;

import com.pranesh.hz.assignment.newsarchivescrapper.model.ScrappedArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TheHinduScrapperService implements INewsArchiveScrapperService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TheHinduScrapperService.class);

    private static final String archiveLink = "https://www.thehindu.com/archive/";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssXXX");

    @Override
    public List<ScrappedArticle> doScrap() {
        Document document = fetchHtmlDocument(archiveLink);
        if (document == null) {
            return null;
        }
        List<ScrappedArticle> scrappedArticles = new ArrayList<>();
        Element archiveWebContainer = document.getElementById("archiveWebContainer");
        Elements monthLinksElements = archiveWebContainer.getElementsByTag("a");
        monthLinksElements.subList(0, 3).forEach(element -> {
            System.out.println("Month list");
            String monthLink = element.attr("href");
            Document monthArchivesDoc = fetchHtmlDocument(monthLink);
            if (monthArchivesDoc == null) {
                return;
            }
            Elements dateLinksElements = monthArchivesDoc.getElementsByClass("archiveTable").first()
                    .getElementsByTag("tbody").first().getElementsByTag("a");
            dateLinksElements.subList(0, 1).forEach(dateLinkElement -> {
                System.out.println("Date list");
                String dateLink = dateLinkElement.attr("href");
                if (!StringUtils.isEmpty(dateLink)) {
                    List<ScrappedArticle> dateArticles = scrapArticles(dateLink);
                    if (dateArticles != null) {
                        scrappedArticles.addAll(dateArticles);
                    }
                }
            });
        });
        return scrappedArticles;
    }

    @Override
    public List<ScrappedArticle> doScrap(Date from, Date to) {
        return null;
    }


    @Nullable
    private Document fetchHtmlDocument(String link) {
        try {
            return Jsoup.connect(link).get();
        } catch (IOException e) {
            LOGGER.error("Couldn't get content from link=[" + link + "]", e);
            return null;
        }
    }

    @Nullable
    private List<ScrappedArticle> scrapArticles(String dateArchiveLink) {
        Document dateArchiveDoc = fetchHtmlDocument(dateArchiveLink);
        if (dateArchiveDoc == null) {
            return null;
        }
        List<ScrappedArticle> scrappedArticles = new ArrayList<>();
        dateArchiveDoc.getElementsByClass("archive-list").subList(0, 2).forEach(sectionElement -> {
            sectionElement.getElementsByTag("a").forEach(articleLinkElement -> {
                String articleLink = articleLinkElement.attr("href");
                Document articleDoc = fetchHtmlDocument(articleLink);
                if (articleDoc == null) {
                    return;
                }
                Elements metaTags = articleDoc.getElementsByTag("meta");
                String title = null;
                String description = null;
                String authorName = null;
                Date publishDate = null;
                for (Element metaTag : metaTags) {
                    if (title == null && metaTag.attr("name").equals("title")) {
                        title = Parser.unescapeEntities(metaTag.attr("content"), true);
                        continue;
                    }
                    if (description == null && metaTag.attr("name").equals("description")) {
                        description = Parser.unescapeEntities(metaTag.attr("content"), true);
                        continue;
                    }
                    if (publishDate == null && metaTag.attr("name").equals("publish-date")) {
                        try {
                            publishDate = dateFormat.parse(metaTag.attr("content"));
                        } catch (ParseException e) {
                            LOGGER.error("Couldn't parse publish-date", e);
                            publishDate = new Date();
                        }
                        continue;
                    }
                    if (authorName == null && metaTag.attr("property").equals("article:author")) {
                        authorName = Parser.unescapeEntities(metaTag.attr("content"), true);
                    }
                    if (title != null && description != null && publishDate != null && authorName != null) {
                        break;
                    }
                }
                ScrappedArticle article = new ScrappedArticle(title, description, authorName, publishDate);
                scrappedArticles.add(article);
                LOGGER.info("Scrapped title=[" + title + "]");
            });
        });
        return scrappedArticles;
    }
}
