package com.yaoiyun.yyscrape.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64ImageUtils {

    private Base64ImageUtils() { }

    public static BufferedImage convertToBufferedImage(String b64ImageString) throws IOException {
        byte[] b64ImageInBytes = Base64.getDecoder().decode(b64ImageString);
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(b64ImageInBytes));

        if (image == null)
            throw new IllegalArgumentException("The provided base64 string does not contain valid encoded image data.");

        return image;
    }

    public static byte[] convertToByteArray(String b64ImageString) {
        return Base64.getDecoder().decode(b64ImageString);
    }

    public static BufferedImage merge(String base64ImageString1, String base64ImageString2) throws IOException {
        return merge(base64ImageString1, base64ImageString2, MergeDirection.HORIZONTAL, false);
    }

    public static BufferedImage merge(String base64ImageString1, String base64ImageString2, boolean reversed) throws IOException {
        return merge(base64ImageString1, base64ImageString2, MergeDirection.HORIZONTAL, reversed);
    }

    public static BufferedImage merge(String base64ImageString1, String base64ImageString2, MergeDirection mergeDirection, boolean reversed) throws IOException {
        BufferedImage image1 = convertToBufferedImage(base64ImageString1);
        BufferedImage image2 = convertToBufferedImage(base64ImageString2);

        int mergedWidth = mergeDirection == MergeDirection.HORIZONTAL
                ? image1.getWidth() + image2.getWidth()
                : Math.max(image1.getWidth(), image2.getWidth());

        int mergedHeight = mergeDirection == MergeDirection.HORIZONTAL
                ? Math.max(image1.getHeight(), image2.getHeight())
                : image1.getHeight() + image2.getHeight();

        BufferedImage merged = new BufferedImage(mergedWidth, mergedHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D canvas = merged.createGraphics();

        if (mergeDirection == MergeDirection.HORIZONTAL) {
            if (reversed) {
                canvas.drawImage(image2, 0, 0, null);
                canvas.drawImage(image1, image2.getWidth(), 0, null);
            } else {
                canvas.drawImage(image1, 0, 0, null);
                canvas.drawImage(image2, image1.getWidth(), 0, null);
            }
        } else if (mergeDirection == MergeDirection.VERTICAL) {
            canvas.drawImage(image1, 0, 0, null);
            canvas.drawImage(image2, 0, image1.getHeight(), null);
        }

        canvas.dispose();

        return merged;
    }

}
