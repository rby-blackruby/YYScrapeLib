package com.yaoiyun.yyscrape.model;

public abstract class ScrapableContent {
    private final String name;
    private final String url;
    private final ScrapableContentType contentType;

    public ScrapableContent(String name, String url, ScrapableContentType contentType) {
        this.name = name;
        this.url = url;
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public ScrapableContentType getContentType() {
        return contentType;
    }
}

