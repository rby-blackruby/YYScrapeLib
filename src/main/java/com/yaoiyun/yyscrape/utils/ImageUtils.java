package com.yaoiyun.yyscrape.utils;

import com.yaoiyun.yyscrape.scraper.exception.ImageProcessException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class ImageUtils {
    private static final Logger LOGGER = Logger.getLogger(ImageUtils.class.getName());

    private ImageUtils() {}

    public static List<byte[]> splitLargeImage(BufferedImage largeImage, String imageExtension, int imageHeightThreshold) {
        LOGGER.info("Creating subimages...");
        List<BufferedImage> subimages = createSubimages(largeImage, imageHeightThreshold);

        LOGGER.info("Converting subimages to byte arrays...");
        List<byte[]> subimagesAsByteArr = subimages.stream()
                .map(s -> convertImageToByteArr(s, imageExtension))
                .toList();

        LOGGER.info("Done.");
        return subimagesAsByteArr;
    }

    public static String detectImageExtensionFromUrl(String imageUrl) {
        return Arrays.asList(imageUrl.split( "\\.")).getLast();
    }

    private static byte[] convertImageToByteArr(BufferedImage image, String imageExtension) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            if(!ImageIO.write(image, imageExtension, baos))
                throw new ImageProcessException("Failed to write image to baos, ImageIO.write returned false!!");
        } catch(IOException e) {
            throw new ImageProcessException("Failed to write image to baos.", e);
        }

        return baos.toByteArray();
    }

    private static List<BufferedImage> createSubimages(BufferedImage largeImage, int imageHeightThreshold) {
        int splitImagesCount = Math.ceilDiv(largeImage.getHeight(), imageHeightThreshold);
        int individualHeight = Math.floorDiv(largeImage.getHeight(), splitImagesCount);
        List<BufferedImage> splitBufferedImages = new ArrayList<>();

        for(int i = 0; i < splitImagesCount; i++) {
            BufferedImage splitImage;

            if(i == splitImagesCount - 1) {
                splitImage = largeImage.getSubimage(0, individualHeight * (splitImagesCount - 1),
                        largeImage.getWidth(), largeImage.getHeight() - (individualHeight * (splitImagesCount - 1)));
            } else {
                splitImage = largeImage.getSubimage(0, individualHeight * i, largeImage.getWidth(), individualHeight);
            }

            splitBufferedImages.add(splitImage);
        }

        return splitBufferedImages;
    }
}
