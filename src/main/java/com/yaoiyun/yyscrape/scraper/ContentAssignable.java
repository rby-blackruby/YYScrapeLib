package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;

import java.util.List;

public interface ContentAssignable {
    ScrapableContent getAssignedContent();
    ScrapableContentType getSupportedContentType();
}
