package com.yaoiyun.yyscrape.scraper.content;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;
import com.yaoiyun.yyscrape.scraper.AbstractScraperBase;
import com.yaoiyun.yyscrape.scraper.ContentScraper;
import com.yaoiyun.yyscrape.scraper.ScrapeResult;
import com.yaoiyun.yyscrape.utils.Base64ImageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManhwaImageContentScraper extends AbstractScraperBase implements ContentScraper {
    // private final String imageFilterKeyword = "data:image/jpeg;base64,";

    public ManhwaImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
    }

    @Override
    public List<ScrapeResult> getContents(String contentUrl) {
        throw new UnsupportedOperationException(this.getClass().getName() + "getContets not implemented.");
    }

}
