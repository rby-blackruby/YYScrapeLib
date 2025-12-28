# YYSLib

A Java-based media archival core library supporting image, video, and text content, designed with a modular, extensible architecture.

## Disclaimer

This project is provided **solely for educational, research, or interoperability purposes**.  
The authors do not endorse or condone illegal use of this software, including downloading or distributing copyrighted material without rights or authorization, and must not be used to collect or redistribute copyrighted material without authorization from the rights holder.

## Key Features
- **Link Discovery**: Extract URLs/links from content pages
- **Content Archival**: Retrieve and archive authorized content (images, text, etc.)
- **Factory-Based Dispatch**: Runtime type checking automatically selects correct implementation
- **Scraper Registry**: Easy to add website specific archiver implementations.

## Example Usage

### Creating & registering website specific link finder

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

### Discovering content urls with link finder

```java
// Main.java
// Example assumes you have permission to access and archive the target content
ScrapableContent content = new ScrapableContentImpl("customwebsite-image", "https://customwebsite.com/somepage", ScrapableContentType.IMAGE);

LinkScraper linkScraper = LinkScraperFactory.getLinkScraperFor(content);
List<String> urls = linkScraper.discoverContentUrls();
```

## Dependencies

- **Selenium WebDriver** 4.39.0
- **JUnit Jupiter** 5.14.1
- **Java 21** 
