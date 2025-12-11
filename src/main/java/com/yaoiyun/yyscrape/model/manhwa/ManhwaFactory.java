package com.yaoiyun.yyscrape.model.manhwa;

import com.yaoiyun.yyscrape.utils.UrlUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Constructor;

public class ManhwaFactory {
    private static Map<String, Class<? extends Manhwa>> manhwaRegistry = new ConcurrentHashMap<>();

    static {
        manhwaRegistry.put("mangabuddy.com", Mangabuddy.class);
        manhwaRegistry.put("zinchanmangas.net", Zinchanmangas.class);
        manhwaRegistry.put("battwo.com", Battwo.class);
    }

    // TODO: more elaborate exception handling
    public static Manhwa getManhwa(String name, String url) {
        String baseUrl = UrlUtils.getBaseUrl(url);
        try {
            return manhwaRegistry.get(baseUrl)
                    .getDeclaredConstructor(String.class, String.class)
                    .newInstance(name, url);
        } catch (Exception e) {
            throw new RuntimeException("" + e);
        }
    }

    public static void register(String baseUrl, Class<? extends Manhwa> implementationClass) {
        manhwaRegistry.put(baseUrl, implementationClass);
    }
}
