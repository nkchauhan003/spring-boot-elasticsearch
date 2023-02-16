package com.cb.mappings;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ArticleQuery {
    private String id;
    private String title;
    private String content;
    private String categoryId;
    private String authorId;
    private int size;
}
