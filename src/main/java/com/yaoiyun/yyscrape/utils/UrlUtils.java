package com.yaoiyun.yyscrape.utils;

public class UrlUtils {

    // TODO split the url with and get the [1] element, that should be the xyz.com base url part.
    public static String getBaseUrl(String url) {
        return "";
    }

    public static String getSanitizedUrl(String url) {
        if(url.endsWith("/")) {
            return url.toLowerCase();
        }

        return url.toLowerCase() + "/";
    }
}
