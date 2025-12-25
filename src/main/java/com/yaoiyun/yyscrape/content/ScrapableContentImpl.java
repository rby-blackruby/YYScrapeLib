package com.yaoiyun.yyscrape.content;

import com.yaoiyun.yyscrape.utils.UrlUtils;

public class ScrapableContentImpl implements ScrapableContent {
    private final String name;
    private final String url;
    private final ScrapableContentType contentType;

    public ScrapableContentImpl(String name, String url, ScrapableContentType contentType) {
        this.name = name;
        this.url = UrlUtils.getSanitizedUrl(url);
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

