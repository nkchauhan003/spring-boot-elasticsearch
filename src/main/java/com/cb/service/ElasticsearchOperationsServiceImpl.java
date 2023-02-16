package com.cb.service;

import com.cb.mappings.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchOperationsServiceImpl implements ElasticsearchOperationsService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @Override
    public Article create(Article article) {
        return elasticsearchOperations.save(article);
    }

    @Override
    public Article retrieve(String id) {
        return elasticsearchOperations.get(id, Article.class);
    }

    @Override
    public UpdateResponse update(Article article) {
        return elasticsearchOperations.update(article);
    }

    @Override
    public String delete(String id) {
        elasticsearchOperations.delete(new Article(id));
        return "Done";
    }
}
