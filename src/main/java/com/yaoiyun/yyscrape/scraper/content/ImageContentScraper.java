package com.yaoiyun.yyscrape.scraper.content;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;
import com.yaoiyun.yyscrape.scraper.AbstractScraperBase;
import com.yaoiyun.yyscrape.scraper.ContentScraper;
import com.yaoiyun.yyscrape.scraper.ScrapeResult;
import com.yaoiyun.yyscrape.scraper.action.ScrapeActions;
import com.yaoiyun.yyscrape.scraper.exception.CloudflareBlockedException;
import com.yaoiyun.yyscrape.scraper.exception.HttpResponseException;
import com.yaoiyun.yyscrape.scraper.filter.ImageUrlFilter;
import com.yaoiyun.yyscrape.utils.ImageUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageContentScraper extends AbstractScraperBase implements ContentScraper {
    private static final Logger LOGGER = Logger.getLogger(ImageContentScraper.class.getName());
    private ImageUrlFilter imageUrlFilter = new ImageUrlFilter(List.of(""), List.of(".webp", ".jpeg", ".jpg", ".png"));
    private ScrapeActions scrapeActions = new ScrapeActions() {
        @Override
        public void onPageLoadedAction(WebDriver webDriver) {

        }
    };
    private static final int IMAGE_SPLITTING_THRESHOLD_PX = 15000; //px

    public ImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
    }

    public ImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent, ImageUrlFilter imageUrlFilter) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
        this.imageUrlFilter = imageUrlFilter;
    }

    public ImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent, ScrapeActions scrapeActions) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
        this.scrapeActions = scrapeActions;
    }

    public ImageContentScraper(WebDriver webDriver, ScrapableContent assignedContent, ImageUrlFilter imageUrlFilter, ScrapeActions scrapeActions) {
        super(webDriver, assignedContent, ScrapableContentType.IMAGE);
        this.imageUrlFilter = imageUrlFilter;
        this.scrapeActions = scrapeActions;
    }

    @Override
    public List<ScrapeResult> getContents(String contentUrl) {
        final List<ScrapeResult> scrapedImages = new ArrayList<>();

        LOGGER.info(() -> "Currently scraping content for " + this.getAssignedContent().getName());
        LOGGER.info(() -> "Opening website with contentUrl: " + contentUrl);
        this.getWebDriver().get(contentUrl);
        this.getWebDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        if(super.isCloudflareBlocked()) {
            throw new CloudflareBlockedException("Manhwa link scraper is possibly being " +
                    "blocked by Cloudflare. URL: " + this.getAssignedContent().getUrl());
        }

        scrapeActions.onPageLoadedAction(this.getWebDriver());

        LOGGER.info("Searching for image content urls");
        final List<WebElement> pageImageContentList = this.getWebDriver().findElements(By.cssSelector("img"));
        final List<String> manhwaImageUrls = filterImageUrls(pageImageContentList);

        if(manhwaImageUrls.isEmpty()) {
            LOGGER.warning("Found 0 images. This might be caused by the website being empty or by an invalid ImageUrlFilter");
            // Prematurely return empty ScrapeResult array to stop the scrape logic from progressing to
            // creating HTTP clients and collecting cookies and etc.
            return scrapedImages;
        }

        LOGGER.info(() -> "Found " + manhwaImageUrls.size() + " images with urls: ");
        manhwaImageUrls.forEach(LOGGER::info);

        Set<Cookie> cookies = this.getWebDriver().manage().getCookies();

        try(HttpClient client = HttpClient.newHttpClient()) {
            for(String imageUrl : manhwaImageUrls) {
                LOGGER.info(() -> "Trying to collect image data from " + imageUrl);
                final URI imageURI = URI.create(imageUrl);
                HttpRequest request = buildHttpRequestFor(imageURI, cookies);

                // Grab images
                final HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
                LOGGER.log(Level.INFO, "Received response with status code: {0}", response.statusCode());

                if(response.statusCode() < 200 || response.statusCode() >= 300) {
                    throw new HttpResponseException(response.statusCode(),
                            "Response returned non-200 status code while trying to download " + imageUrl);
                }

                // Detect image format name from the image url
                final String imageExtension = Arrays.asList(imageUrl.split( "\\.")).getLast();
                LOGGER.info(() -> "Detected image extension for image: " + imageExtension + " with url: " + imageUrl);

                // Check image size if it needs splitting then return ScrapeResult objects
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(response.body()));
                if(image.getHeight() > IMAGE_SPLITTING_THRESHOLD_PX) {
                    LOGGER.info("Image exceeds splitting threshold. Trying to split image into subimages...");
                    List<byte[]> splitImages = ImageUtils.splitLargeImage(image, imageExtension);
                    splitImages.forEach(splitImage -> scrapedImages.add(ScrapeResult.ofImage(splitImage, imageExtension)));
                } else {
                    scrapedImages.add(ScrapeResult.ofImage(response.body(), imageExtension));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to download image from website: " + contentUrl, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to download image from website: " + contentUrl, e);
        }

        return scrapedImages;
    }

    protected List<String> filterImageUrls(List<WebElement> webElements) {
        return webElements.stream()
                .map(element -> element.getAttribute("src"))
                .filter(Objects::nonNull)
                .filter(imageUrl -> {
                    for(String pathKeyword : imageUrlFilter.pathKeywords()) {
                        if(imageUrl.contains(pathKeyword)) return true;
                    }
                    return false;
                })
                .filter(imageUrl -> {
                    for(String allowedImageExtension : imageUrlFilter.allowedImageExtensions()) {
                        if(imageUrl.contains(allowedImageExtension)) return true;
                    }
                    return false;
                })
                .toList();
    }

    protected HttpRequest buildHttpRequestFor(URI imageURI, Set<Cookie> cookies) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        cookies.forEach(cookie -> requestBuilder.header(cookie.getName(), cookie.getValue()));

        HttpRequest request = requestBuilder.header("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                                "Chrome/91.0.4472.124 Safari/537.36")
                .header("Referer", this.getWebDriver().getCurrentUrl())
                .uri(imageURI)
                .build();

        return request;
    }

}
