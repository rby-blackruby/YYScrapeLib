package com.yaoiyun.yyscrape.scraper.implementations;

import com.yaoiyun.yyscrape.content.Series;
import com.yaoiyun.yyscrape.scraper.ContentExtractor;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class SeriesContentExtractor extends ContentExtractor<Series> {

    public SeriesContentExtractor(WebDriver webDriver, short executionThreads, Series assignedContent) {
        super(webDriver, executionThreads, assignedContent, Series.class);
    }

    @Override
    public List<byte[]> getContents(String contentUrl) {
        return List.of();
    }

    @Override
    public String getAssignedContentUrl() {
        return "";
    }

    @Override
    public void close() throws Exception {

    }
}
