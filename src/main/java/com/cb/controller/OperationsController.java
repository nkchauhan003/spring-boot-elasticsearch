package com.cb.controller;

import com.cb.mappings.Article;
import com.cb.service.ElasticsearchOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/operations")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OperationsController {

    @Autowired
    private ElasticsearchOperationsService elasticsearchOperationsService;

    @PostMapping(value = "/", consumes = "application/json")
    public Article create(@RequestBody Article article) {
        return elasticsearchOperationsService.create(article);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Article retrieve(@PathVariable String id) {
        return elasticsearchOperationsService.retrieve(id);
    }

    @PutMapping(value = "/", consumes = "application/json")
    public UpdateResponse update(@RequestBody Article article) {
        return elasticsearchOperationsService.update(article);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable String id) {
        elasticsearchOperationsService.delete(id);
        return "Done";
    }
}
