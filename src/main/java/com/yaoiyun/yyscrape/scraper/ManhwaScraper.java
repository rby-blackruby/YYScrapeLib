package com.yaoiyun.yyscrape.scraper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManhwaScraper extends AbstractScraper {

    private ManhwaScraper(Builder builder) {
        super(builder);
    }

    @Override
    public void run() {
        this.getWebDriver().get(this.getAssignedContent().getUrl());
    }

    public static class Builder extends AbstractScraper.Builder<Builder> {

        @Override
        public ManhwaScraper build() {
            return new ManhwaScraper(this);
        }
    }
}
