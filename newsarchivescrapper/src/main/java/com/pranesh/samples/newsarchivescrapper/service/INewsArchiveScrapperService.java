package com.pranesh.samples.newsarchivescrapper.service;

import com.pranesh.samples.newsarchivescrapper.model.ScrappedArticle;

import java.util.Date;
import java.util.function.Consumer;

public interface INewsArchiveScrapperService {

    void doScrap(Consumer<ScrappedArticle> consumer);

    void doScrap(Date from, Date to, Consumer<ScrappedArticle> consumer);
}
