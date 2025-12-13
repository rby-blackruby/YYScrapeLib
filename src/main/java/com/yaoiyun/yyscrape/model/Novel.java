package com.yaoiyun.yyscrape.model;

public abstract class Novel extends ScrapableContent {

    public Novel(String name, String url, ScrapableContentType contentType) {
        super(name, url, contentType);
    }
}
