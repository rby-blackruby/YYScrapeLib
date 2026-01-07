package com.yaoiyun.yyscrape.scraper.content;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;
import com.yaoiyun.yyscrape.scraper.AbstractScraperBase;
import com.yaoiyun.yyscrape.scraper.ContentScraper;
import com.yaoiyun.yyscrape.scraper.ScrapeResult;
import com.yaoiyun.yyscrape.scraper.action.ScrapeActions;
import com.yaoiyun.yyscrape.scraper.exception.CloudflareBlockedException;
import com.yaoiyun.yyscrape.scraper.exception.ImageProcessException;
import com.yaoiyun.yyscrape.utils.Base64ImageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Base64ImageContentScraper extends AbstractScraperBase implements ContentScraper {
    private static final Logger LOGGER = Logger.getLogger(Base64ImageContentScraper.class.getName());
    // make it user specifiable and extract image format from base64 string
    private final String imageFilterKeyword = "data:image/jpeg;base64,";
    private ScrapeActions scrapeActions = new ScrapeActions() {
        @Override
        public void onPageLoadedAction(WebDriver webDriver) {

        }
    };

    public Base64ImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
    }

    public Base64ImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent, ScrapeActions scrapeActions) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
        this.scrapeActions = scrapeActions;
    }

    @Override
    public List<ScrapeResult> getContents(String contentUrl) {
        LOGGER.info(() -> "Opening website: " + contentUrl);
        this.getWebDriver().get(contentUrl);
        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        if(super.isCloudflareBlocked()) {
            throw new CloudflareBlockedException(this.getClass().getName() + " is possibly being " +
                    "blocked by Cloudflare. URL: " + this.getAssignedContent().getUrl());
        }

        scrapeActions.onPageLoadedAction(this.getWebDriver());

        LOGGER.info("Looking for images...");
        List<String> b64Images = this.getWebDriver().findElements(By.cssSelector("img")).stream()
                .map(image -> image.getAttribute("src"))
                .filter(Objects::nonNull)
                .filter(image -> image.contains(imageFilterKeyword))
                .toList();

        List<ScrapeResult> imagesList = new ArrayList<>();

        final Iterator<String> images64 = b64Images.iterator();
        while (images64.hasNext()) {
            final String first = images64.next().replace(imageFilterKeyword, "");
            final String second = images64.next().replace(imageFilterKeyword, "");

            try{
                BufferedImage mergedImage = Base64ImageUtils.merge(first, second, true);
                if(mergedImage.getHeight() == 0 || mergedImage.getWidth() == 0) {
                    throw new ImageProcessException("Merged image contains invalid image data: height or width is zero.");
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final boolean writeSuccess = ImageIO.write(mergedImage, "webp", baos);
                if (!writeSuccess) {
                    throw new ImageProcessException("Could not write image. Check for valid color space (no alpha channel)");
                }

                ScrapeResult result = ScrapeResult.ofImage(baos.toByteArray(), "webp");
                imagesList.add(result);

            } catch(Exception e) {
                LOGGER.severe("Failed to add image to imageList.");
                e.printStackTrace();
            }
        }
        LOGGER.info("Found " + imagesList.size() + " images.");

        return imagesList;
    }
}
