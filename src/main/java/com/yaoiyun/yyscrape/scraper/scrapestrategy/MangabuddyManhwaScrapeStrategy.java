package com.yaoiyun.yyscrape.scraper.scrapestrategy;

import java.util.List;

public class MangabuddyManhwaScrapeStrategy implements ManhwaScrapeStrategy {

    public MangabuddyManhwaScrapeStrategy() {

    }

    @Override
    public List<String> getValidImageExtensions() {
        return List.of(".webp", ".jpeg", ".jpg", ".png");
    }

    @Override
    public List<String> getRequiredKeywordsInImageUrl() {
        return List.of("/manga");
    }

}
