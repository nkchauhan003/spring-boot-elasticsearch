package com.cb.service;

import com.cb.mappings.Article;
import com.cb.mappings.ArticleQuery;

import java.io.IOException;
import java.util.List;

public interface ElasticsearchClientService {

    List<Article> fetchArticlesWithMustQuery(ArticleQuery articleQuery) throws IOException;

    List<Article> fetchArticlesWithShouldQuery(ArticleQuery articleQuery) throws IOException;

    List<Article> textSearch(List<String> fields, String term, int size) throws IOException;
}
