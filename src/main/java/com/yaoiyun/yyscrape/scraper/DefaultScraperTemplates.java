package com.yaoiyun.yyscrape.scraper;

import com.yaoiyun.yyscrape.scraper.content.ManhwaImageContentScraper;
import com.yaoiyun.yyscrape.scraper.content.ZinchangmangaImageContentScraper;
import com.yaoiyun.yyscrape.scraper.link.ManhwaLinkScraper;
import com.yaoiyun.yyscrape.scraper.link.ZinchangmangaManhwaLinkScraper;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DefaultScraperTemplates {

    public static void loadAll() {
        loadDefaultContentScrapers();
        loadDefaultLinkScrapers();
    }

    public static void loadDefaultLinkScrapers() {
        LinkScraperFactory.register("mangabuddy.com", scrapableContent -> {
            return new ManhwaLinkScraper(new FirefoxDriver(), scrapableContent);
        });

        LinkScraperFactory.register("zinchangmanga.net", scrapableContent -> {
            return new ZinchangmangaManhwaLinkScraper(new FirefoxDriver(), scrapableContent);
        });

        LinkScraperFactory.register("yaoiscan.com", scrapableContent -> {
            return new ManhwaLinkScraper(new FirefoxDriver(), scrapableContent);
        });
    }

    public static void loadDefaultContentScrapers() {
        ContentScraperFactory.register("zinchangmanga.net", scrapableContent -> {
            return new ZinchangmangaImageContentScraper(new FirefoxDriver(), scrapableContent);
        });
    }
}
