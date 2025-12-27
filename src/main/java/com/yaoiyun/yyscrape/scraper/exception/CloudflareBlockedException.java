package com.yaoiyun.yyscrape.scraper.exception;

public class CloudflareBlockedException extends RuntimeException {
    public CloudflareBlockedException(String message) {
        super(message);
    }
}
