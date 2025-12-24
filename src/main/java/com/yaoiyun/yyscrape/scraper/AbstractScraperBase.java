package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;
import org.openqa.selenium.WebDriver;

public abstract class AbstractScraperBase implements ContentAssignable, AutoCloseable {
    private WebDriver webDriver;
    private ScrapableContent assignedContent;
    private ScrapableContentType supportedContentType;
    // private final List<String> supportedHostsList;

    public AbstractScraperBase(WebDriver webDriver, ScrapableContent assignedContent,
                               ScrapableContentType supportedContentType) {
        if(assignedContent.getContentType() != supportedContentType) {
            webDriver.close();
            throw new IllegalArgumentException("Assigned content type is not supported by this scraper.");
        }

        this.webDriver = webDriver;
        this.assignedContent = assignedContent;
        this.supportedContentType = supportedContentType;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public ScrapableContent getAssignedContent() {
        return assignedContent;
    }

    @Override
    public ScrapableContentType getSupportedContentType() {
        return supportedContentType;
    }

    @Override
    public void close() {
        try { this.webDriver.quit(); } catch(Exception ignored) {}
    }

}
