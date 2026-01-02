package com.yaoiyun.yyscrape.scraper.link;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class ZinchangmangaManhwaLinkScraper extends ManhwaLinkScraper {
    public ZinchangmangaManhwaLinkScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent);
    }

    @Override
    protected void postLoadAction() {
        WebElement showMoreButton = this.getWebDriver().findElement(By.className("chapter-readmore"));
        showMoreButton.click();
        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }
}
