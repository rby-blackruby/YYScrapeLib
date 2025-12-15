package com.yaoiyun.yyscrape.content.assembly;

import com.yaoiyun.yyscrape.content.Manhwa;
import com.yaoiyun.yyscrape.content.configs.BattwoManhwaConfig;
import com.yaoiyun.yyscrape.content.configs.MangabuddyManhwaConfig;
import com.yaoiyun.yyscrape.content.configs.ManhwaConfig;
import com.yaoiyun.yyscrape.content.configs.ZinchanmangasManhwaConfig;
import com.yaoiyun.yyscrape.utils.UrlUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ManhwaFactory {
    private static Map<String, ManhwaConfig> manhwaConfigRegistry = new ConcurrentHashMap<>();

    static {
        manhwaConfigRegistry.put("mangabuddy.com", new MangabuddyManhwaConfig());
        manhwaConfigRegistry.put("zinchanmangas.net", new ZinchanmangasManhwaConfig());
        manhwaConfigRegistry.put("battwo.com", new BattwoManhwaConfig());
    }

    private ManhwaFactory() {

    }

    public static Manhwa getManhwa(String name, String url) {
        String baseUrl = UrlUtils.getBaseUrl(url);
        ManhwaConfig manhwaConfig = manhwaConfigRegistry.get(baseUrl);

        return new Manhwa(name, url, manhwaConfig);
    }

    public static void register(String baseUrl, ManhwaConfig configImplementationClass) {
        manhwaConfigRegistry.putIfAbsent(baseUrl, configImplementationClass);
    }
}
