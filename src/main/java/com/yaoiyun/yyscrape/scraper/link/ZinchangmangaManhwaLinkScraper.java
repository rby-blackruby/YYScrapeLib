package com.yaoiyun.yyscrape.scraper.link;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.scraper.exception.CloudflareBlockedException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

public class ZinchangmangaManhwaLinkScraper extends ManhwaLinkScraper {
    public ZinchangmangaManhwaLinkScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent);
    }

    @Override
    public List<String> discoverContentUrls() {
        this.getWebDriver().get(this.getAssignedContent().getUrl());
        // TODO: use explicit wait instead of implicit x seconds
        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // an entirely new class was required just for clicking this button, maybe do
        // something in the ManhwaLinkScraper class instead?
        WebElement showMoreButton = this.getWebDriver().findElement(By.className("chapter-readmore"));
        showMoreButton.click();
        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        List<WebElement> elements = this.findWebsiteElementsByCssSelector("a");
        for(var element : elements) {
            if(element.getText().contains("Cloudflare")) {
                throw new CloudflareBlockedException("Manhwa link scraper blocked by Cloudflare. URL: " + this.getAssignedContent().getUrl());
            }
        }

        return getFilteredChapterUrls(elements);
    }
}
