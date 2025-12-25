# YYScrapeLib

A Java-based web scraping core library for extracting content from manga, manhwa, novels, and other serialized content platforms, designed with a modular, extensible architecture.

## Overview

## Key Features
- **Link Discovery**: Extract URLs/links from content pages
- **Content Scraping**: Download and process actual content (images, text, etc.)
- **Factory-Based Dispatch**: Runtime type checking automatically selects correct implementation
- **Scraper Registry**: Easy to add website specific scraper implementations.

## Example Usage

### Creating & registering website specific link scraper

```java
// CustomWebsiteLinkScraper.java
public class CustomWebsiteLinkScraper implements LinkScraper {
    private WebDriver webDriver;
    private ScrapableContent assignedContent;

    public CustomWebsiteLinkScraper(WebDriver webDriver, ScrapableContent assignedContent) {
        this.webDriver = webDriver;
        this.assignedContent = assignedContent;
    }

    @Override
    public List<String> discoverContentUrls() {
        // site specific implementation
        // ...
    }
}

// Main.java
LinkScraperFactory.register("customwebsite.com",scrapableContent ->{
    return new CustomWebsiteLinkScraper(new FirefoxDriver(),scrapableContent);
});
```

### Discovering content urls with link scrapers

```java
// Main.java
ScrapableContent content = new ScrapableContentImpl("customwebsite-image", "https://customwebsite.com/somepage", ScrapableContentType.IMAGE);

LinkScraper linkScraper = LinkScraperFactory.getLinkScraperFor(content);
List<String> urls = linkScraper.discoverContentUrls();
```

## Dependencies

- **Selenium WebDriver** 4.39.0
- **JUnit Jupiter** 5.14.1
- **Java 21** 
