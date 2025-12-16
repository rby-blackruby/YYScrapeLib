package com.yaoiyun.yyscrape.content;

import com.yaoiyun.yyscrape.content.scrapestrategy.BattwoManhwaScrapeStrategy;
import com.yaoiyun.yyscrape.content.scrapestrategy.MangabuddyManhwaScrapeStrategy;
import com.yaoiyun.yyscrape.content.scrapestrategy.ManhwaScrapeStrategy;
import com.yaoiyun.yyscrape.content.scrapestrategy.ZinchanmangasManhwaScrapeStrategy;
import com.yaoiyun.yyscrape.utils.UrlUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ManhwaFactory {
    private static Map<String, ManhwaScrapeStrategy> manhwaConfigRegistry = new ConcurrentHashMap<>();

    static {
        manhwaConfigRegistry.put("mangabuddy.com", new MangabuddyManhwaScrapeStrategy());
        manhwaConfigRegistry.put("zinchanmangas.net", new ZinchanmangasManhwaScrapeStrategy());
        manhwaConfigRegistry.put("battwo.com", new BattwoManhwaScrapeStrategy());
    }

    private ManhwaFactory() {

    }

    public static Manhwa getManhwa(String name, String url) {
        String baseUrl = UrlUtils.getBaseUrl(url);
        ManhwaScrapeStrategy manhwaScrapeStrategy = manhwaConfigRegistry.get(baseUrl);

        return new Manhwa(name, url, manhwaScrapeStrategy);
    }

    public static void register(String baseUrl, ManhwaScrapeStrategy configImplementationClass) {
        manhwaConfigRegistry.putIfAbsent(baseUrl, configImplementationClass);
    }
}
