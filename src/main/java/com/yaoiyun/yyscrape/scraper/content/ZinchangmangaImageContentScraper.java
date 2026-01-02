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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ZinchangmangaImageContentScraper extends AbstractScraperBase implements ContentScraper {
    private static final Logger LOGGER = Logger.getLogger(ZinchangmangaImageContentScraper.class.getName());
    private final String imageFilterKeyword = "data:image/jpeg;base64,";

    public ZinchangmangaImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
    }

    @Override
    public List<ScrapeResult> getContents(String contentUrl) {
        LOGGER.info(() -> "Opening website: " + contentUrl);
        this.getWebDriver().get(contentUrl);
        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        LOGGER.info("Scrolling to the bottom.");
        new Actions(this.getWebDriver()).scrollByAmount(0, 20000).perform();

        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

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
                    throw new RuntimeException("Merged image contains invalid image data: height or width is zero.");
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final boolean writeSuccess = ImageIO.write(mergedImage, "webp", baos);
                if (!writeSuccess) {
                    throw new RuntimeException("Could not write image. Check for valid color space (no alpha channel)");
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
