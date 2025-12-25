package com.yaoiyun.yyscrape.scraper.link;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import org.openqa.selenium.WebDriver;

import java.util.regex.Pattern;

public class BattwoManhwaLinkScraper extends DefaultManhwaLinkScraper{
    private final Pattern chapterUrlRegex = Pattern.compile("ch_(\\d+)(?:[-\\.](\\d+))?(?:[-\\.](\\d+))?");

    public BattwoManhwaLinkScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent);
    }
}
