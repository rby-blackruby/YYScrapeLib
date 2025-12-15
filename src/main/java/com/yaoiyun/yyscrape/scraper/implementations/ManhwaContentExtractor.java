package com.yaoiyun.yyscrape.scraper.implementations;

import com.yaoiyun.yyscrape.content.Manhwa;
import com.yaoiyun.yyscrape.scraper.ContentExtractor;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ManhwaContentExtractor extends ContentExtractor<Manhwa> {

    public ManhwaContentExtractor(WebDriver webDriver, short executionThreads, Manhwa assignedContent) {
        super(webDriver, executionThreads, assignedContent, Manhwa.class);
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
