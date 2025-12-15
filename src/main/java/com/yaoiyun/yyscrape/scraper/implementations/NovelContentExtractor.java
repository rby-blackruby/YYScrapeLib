package com.yaoiyun.yyscrape.scraper.implementations;

import com.yaoiyun.yyscrape.content.Novel;
import com.yaoiyun.yyscrape.scraper.ContentExtractor;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class NovelContentExtractor extends ContentExtractor<Novel> {

    public NovelContentExtractor(WebDriver webDriver, short executionThreads, Novel assignedContent) {
        super(webDriver, executionThreads, assignedContent, Novel.class);
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
