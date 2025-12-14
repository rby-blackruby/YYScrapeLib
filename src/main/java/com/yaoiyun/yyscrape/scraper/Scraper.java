package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.model.ScrapableContent;

import java.util.List;

public interface Scraper<C extends ScrapableContent> extends LinkScraper, ContentScraper {
    C getAssignedContent();
    Class<C> getAssignedContentClass();
}

