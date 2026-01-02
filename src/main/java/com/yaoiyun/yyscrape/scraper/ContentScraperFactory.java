package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.utils.UrlUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ContentScraperFactory {
    private static final Map<String, ScraperSupplier<ContentScraper>> CONTENT_SCRAPER_REGISTRY = new ConcurrentHashMap<>();

    private ContentScraperFactory() {
    }

    public static void register(String websiteBaseUrl, ScraperSupplier<ContentScraper> contentScraperClass) {
        CONTENT_SCRAPER_REGISTRY.putIfAbsent(websiteBaseUrl, contentScraperClass);
    }

    public static ContentScraper getContentScraperFor(ScrapableContent assignedContent) {
        String websiteBaseUrl = UrlUtils.getBaseUrl(assignedContent.getUrl());

        if (!CONTENT_SCRAPER_REGISTRY.containsKey(websiteBaseUrl)) {
            throw new IllegalArgumentException("The provided url is not associated with any scrapper: " + websiteBaseUrl);
        }

        return CONTENT_SCRAPER_REGISTRY.get(websiteBaseUrl).get(assignedContent);
    }
}
