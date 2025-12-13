package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.model.Series;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SeriesScraper extends GenericScraper<Series> {
    public SeriesScraper(WebDriver webDriver, short executionThreads, Series assignedContent) {
        super(webDriver, executionThreads, assignedContent, Series.class);
    }

    @Override
    public List<String> getContentUrls() {
        return List.of();
    }

    @Override
    public List<byte[]> getContents() {
        return List.of();
    }

    @Override
    public void close() throws Exception {

    }
}
