package com.cb.controller;

import com.cb.mappings.Article;
import com.cb.repository.springdata.ArticleRepository;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/articles")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CrudController {

    @Autowired
    private ArticleRepository articleRepository;

    @PostMapping(value = "/", consumes = "application/json")
    public Article create(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Article retrieve(@PathVariable String id) {
        return articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID: " + id));
    }

    @PutMapping(value = "/", consumes = "application/json")
    public Article update(@RequestBody Article article) {
        return articleRepository.save(article);
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable String id) {
        articleRepository.deleteById(id);
        return "Done";
    }
}
