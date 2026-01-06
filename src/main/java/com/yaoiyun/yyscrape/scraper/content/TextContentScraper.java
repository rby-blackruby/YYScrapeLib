package com.yaoiyun.yyscrape.scraper.content;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;
import com.yaoiyun.yyscrape.scraper.AbstractScraperBase;
import com.yaoiyun.yyscrape.scraper.ContentScraper;
import com.yaoiyun.yyscrape.scraper.ScrapeResult;
import com.yaoiyun.yyscrape.scraper.exception.NotImplementedException;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class TextContentScraper extends AbstractScraperBase implements ContentScraper {

    public TextContentScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent, ScrapableContentType.TEXT);
    }

    /*
        TODO: implement text scraping logic
        Feature planned, not yet implemented.
     */
    @Deprecated(since = "-", forRemoval = false)
    @Override
    public List<ScrapeResult> getContents(String contentUrl) {
        throw new NotImplementedException("Text scraping is not yet implemented.");
    }
}
