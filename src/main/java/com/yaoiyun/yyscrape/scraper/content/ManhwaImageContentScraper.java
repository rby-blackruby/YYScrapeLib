package com.yaoiyun.yyscrape.scraper.content;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;
import com.yaoiyun.yyscrape.scraper.AbstractScraperBase;
import com.yaoiyun.yyscrape.scraper.ContentScraper;
import com.yaoiyun.yyscrape.scraper.scrapestrategy.ManhwaScrapeStrategy;
import com.yaoiyun.yyscrape.utils.Base64ImageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManhwaImageContentScraper extends AbstractScraperBase implements ContentScraper {
    private final String imageFilterKeyword = "data:image/jpeg;base64,";

    public ManhwaImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
    }

    @Override
    public List<BufferedImage> getContents(String contentUrl) {
        System.out.print("Opening website: " + contentUrl);
        this.getWebDriver().get(contentUrl);
        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        System.out.println("Scrolling to the bottom.");
        new Actions(this.getWebDriver()).scrollByAmount(0, 20000).perform();

        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        System.out.println("Looking for images...");
        List<String> b64Images = this.getWebDriver().findElements(By.cssSelector("img")).stream()
                .map(image -> image.getAttribute("src"))
                .filter(Objects::nonNull)
                .filter(image -> image.contains(imageFilterKeyword))
                .toList();

        System.out.println("Found the following base64 encoded image strings for " + this.getAssignedContent().getName());
        for(var b64Image : b64Images) {
            System.out.println(b64Image);
        }

        List<BufferedImage> imagesList = new ArrayList<>();
        List<String> base64ImageCache = new ArrayList<>();
        for(String b64Image : b64Images) {
            if(base64ImageCache.size() == 2) {
                try{
                    BufferedImage mergedImage = Base64ImageUtils.merge(base64ImageCache.get(0), base64ImageCache.get(1), true);
                    imagesList.add(mergedImage);
                    base64ImageCache.clear();
                    continue;
                } catch(Exception ignored) {};
            }
            base64ImageCache.add(b64Image.replace(imageFilterKeyword, ""));
        }
        System.out.println("Found " + imagesList.size() + " images.");

        return imagesList;
    }

}
