package com.yaoiyun.yyscrape.scraper.content;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;
import com.yaoiyun.yyscrape.scraper.AbstractScraperBase;
import com.yaoiyun.yyscrape.scraper.ContentScraper;
import com.yaoiyun.yyscrape.scraper.ScrapeResult;
import com.yaoiyun.yyscrape.scraper.exception.NotImplementedException;
import org.openqa.selenium.WebDriver;

import java.util.List;

// Apparently InputStream is what should be used for when downloading video media, needs more testing
// to see how it would work.
public class VideoContentScraper extends AbstractScraperBase implements ContentScraper {

    public VideoContentScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent, ScrapableContentType.VIDEO);
    }

    /*
        TODO: implement text scraping logic
        Feature planned, not yet implemented.
     */
    @Deprecated(since = "-", forRemoval = false)
    @Override
    public List<ScrapeResult> getContents(String contentUrl) {
        throw new NotImplementedException("Video content scraping not yet implemented.");
    }
}
