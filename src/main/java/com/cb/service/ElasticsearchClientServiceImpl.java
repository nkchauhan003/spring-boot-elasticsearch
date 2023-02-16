package com.cb.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.cb.mappings.Article;
import com.cb.mappings.ArticleQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service @Slf4j public class ElasticsearchClientServiceImpl implements ElasticsearchClientService {

    private static String index = "blog";

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public List<Article> fetchArticlesWithMustQuery(ArticleQuery articleQuery) throws IOException {
        List<Query> queries = prepareQueryList(articleQuery);
        SearchResponse<Article> articleSearchResponse = elasticsearchClient.search(req -> req.index(index).size(
                articleQuery.getSize()).query(query -> query.bool(bool -> bool.must(queries))), Article.class);
        return articleSearchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
    }

    @Override
    public List<Article> fetchArticlesWithShouldQuery(ArticleQuery articleQuery) throws IOException {
        List<Query> queries = prepareQueryList(articleQuery);
        SearchResponse<Article> articleSearchResponse = elasticsearchClient.search(req -> req.index(index).size(
                articleQuery.getSize()).query(query -> query.bool(bool -> bool.should(queries))), Article.class);
        return articleSearchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
    }

    @Override
    public List<Article> textSearch(List<String> fields, String term, int size) throws IOException {
        SearchResponse<Article> articleSearchResponse = elasticsearchClient.search(req -> req.index(index).size(
                size).query(query -> query.multiMatch(multiMatchQuery(fields, term))), Article.class);
        return articleSearchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
    }

    private List<Query> prepareQueryList(ArticleQuery articleQuery) {
        var queryMap = Map.of("category_id", articleQuery.getCategoryId(), "author_id", articleQuery.getAuthorId());

        var queries = queryMap.entrySet().stream().filter(entry -> !ObjectUtils.isEmpty(entry.getValue())).map(
                entry -> termQuery(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        log.debug("queries: " + queries);
        return queries;
    }

    public Query termQuery(String field, String value) {
        QueryVariant queryVariant = new TermQuery.Builder().caseInsensitive(true).field(field).value(value).build();
        return new Query(queryVariant);
    }

    public MultiMatchQuery multiMatchQuery(List<String> fields, String value) {
        return new MultiMatchQuery.Builder().fields(fields).query(value).build();
    }
}
