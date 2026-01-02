package com.yaoiyun.yyscrape.scraper.content;

import java.util.List;

public record ImageUrlFilter(String pathKeyword, List<String> allowedImageExtensions) {
}
