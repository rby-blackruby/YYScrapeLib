package com.yaoiyun.yyscrape.scraper.exception;

public class HttpResponseException extends RuntimeException {
    private final int statusCode;

    public HttpResponseException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
