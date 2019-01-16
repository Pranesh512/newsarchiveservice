package com.pranesh.samples.newsarchivescrapper.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SearchResponse<T> {

    private final List<T> data;
}
