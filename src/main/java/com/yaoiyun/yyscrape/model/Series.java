package com.yaoiyun.yyscrape.model;

public abstract class Series extends ScrapableContent {

    public Series(String name, String url, ScrapableContentType contentType) {
        super(name, url, contentType);
    }
}
