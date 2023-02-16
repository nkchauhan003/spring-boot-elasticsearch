package com.cb.controller;

import com.cb.mappings.Article;
import com.cb.mappings.ArticleQuery;
import com.cb.service.ElasticsearchClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/query")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ElasticsearchClientController {

    private static final List<String> fields = List.of("title", "content");

    @Autowired
    private ElasticsearchClientService elasticsearchClientService;

    @PostMapping(value = "/must", produces = "application/json")
    public List<Article> fetchArticlesWithMustQuery(@RequestBody ArticleQuery articleQuery) throws IOException {
        return elasticsearchClientService.fetchArticlesWithMustQuery(articleQuery);
    }

    @PostMapping(value = "/should", produces = "application/json")
    public List<Article> fetchArticlesWithShouldQuery(@RequestBody ArticleQuery articleQuery) throws IOException {
        return elasticsearchClientService.fetchArticlesWithShouldQuery(articleQuery);
    }

    @GetMapping(value = "/{term}", produces = "application/json")
    public List<Article> textSearch(@PathVariable String term) throws IOException {
        return elasticsearchClientService.textSearch(fields, term, 10);
    }
}
