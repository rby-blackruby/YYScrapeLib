package com.yaoiyun.yyscrape.utils;

import java.util.Arrays;

public class UrlUtils {

    // TODO split the url with and get the [1] element, that should be the xyz.com base url part.
    // urlArr is: [https:, , mangabuddy.com] -- actually needs array[2]
    // this is going to give an out of bounds error if the url is not formatted correctly
    public static String getBaseUrl(String url) {
        String[] urlArr = url.split("/");

        if(urlArr.length < 3) {
            throw new IllegalArgumentException("Url with incorrect formatting cannot be split.");
        }

        return urlArr[2];
    }

    public static String getSanitizedUrl(String url) {
        if(url.endsWith("/")) {
            return url.toLowerCase();
        }

        return url.toLowerCase() + "/";
    }
}
