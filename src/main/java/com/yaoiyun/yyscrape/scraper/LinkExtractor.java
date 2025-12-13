package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.model.ScrapableContent;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class LinkExtractor<T extends ScrapableContent> implements LinkScraper, AutoCloseable {
    private final WebDriver webDriver;
    private final T assignedContent;

    private LinkExtractor(Builder<T> builder) {
        this.webDriver = builder.webDriver;
        this.assignedContent = builder.assignedContent;
    }

    @Override
    public List<String> getContentUrls() {
        System.out.println("Opening content url: " + assignedContent.getUrl());
        webDriver.get("https://" + assignedContent.getUrl());
        return List.of("link1", "link2", "link3");
    }

    @Override
    public void close() throws Exception {
        System.out.println(webDriver);
        System.out.println("|||||||||||");

        try {
            webDriver.quit();
        } catch(RuntimeException re) {
            System.out.println("Couldn't close webDriver. Has it been closed before the auto closing function? Error:" + re);
        }
    }

    public WebDriver getWebDriver() { return webDriver; }
    public T getAssignedContent() { return assignedContent; }

    public static class Builder<T extends ScrapableContent> {
        private WebDriver webDriver;
        private T assignedContent;

        public Builder<T> initWebdriver(WebDriver webDriver) {
            this.webDriver = webDriver;
            return this;
        }

        public Builder<T> assignContent(T assignedContent) {
            this.assignedContent = assignedContent;
            return this;
        }

        public LinkExtractor<T> build() {
            return new LinkExtractor<>(this);
        }
    }
}
