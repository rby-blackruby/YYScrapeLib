package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.content.ScrapableContent;

public interface Scraper<T extends ScrapableContent> extends LinkScraper<T>, ContentScraper<T> {
}

