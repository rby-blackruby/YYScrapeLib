package com.yaoiyun.yyscrape.content.assembly;

import com.yaoiyun.yyscrape.content.ScrapableContent;
import com.yaoiyun.yyscrape.content.ScrapableContentType;

public class ScrapableContentFactory {

    private ScrapableContentFactory() {

    }

    public static <T extends ScrapableContent> T getContent(String name, String url, ScrapableContentType contentType) {
        ScrapableContent content = switch(contentType) {
            case ScrapableContentType.IMAGE -> ManhwaFactory.getManhwa(name, url);
            case ScrapableContentType.TEXT, ScrapableContentType.VIDEO ->
                    throw new IllegalArgumentException("Factory is not implemented for this type of content");
        };

        @SuppressWarnings("unchecked")
        T c = (T) content;
        return c;
    }
}
