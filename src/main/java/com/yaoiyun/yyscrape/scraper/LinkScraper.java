package com.yaoiyun.yyscrape.scraper;

import java.util.List;

public interface LinkScraper extends AutoCloseable {
    List<String> discoverContentUrls();
}
