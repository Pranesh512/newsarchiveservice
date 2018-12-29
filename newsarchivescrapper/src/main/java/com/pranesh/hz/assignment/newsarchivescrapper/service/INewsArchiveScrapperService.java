package com.pranesh.hz.assignment.newsarchivescrapper.service;

import com.pranesh.hz.assignment.newsarchivescrapper.model.ScrappedArticle;

import java.util.Date;
import java.util.List;

public interface INewsArchiveScrapperService {

    List<ScrappedArticle> doScrap();

    List<ScrappedArticle> doScrap(Date from, Date to);
}
