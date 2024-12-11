package org.primefaces.test;

import javax.imageio.*;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class JpegGenerator {
    public static void main(String[] args) throws IOException {
        int width = 50000;
        int height = 50000;
        int blockSize = 1000;

        System.out.println("Generating JPEG image...");
        File outputFile = new File("C:\\workspace\\jpg.jpg");
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(new FileOutputStream(outputFile))) {
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.95f);

            BufferedImage image = new BufferedImage(blockSize, blockSize, BufferedImage.TYPE_INT_RGB);
            Random rand = new Random();

            for (int y = 0; y < height; y += blockSize) {
                for (int x = 0; x < width; x += blockSize) {
                    for (int j = 0; j < blockSize && (y + j) < height; j++) {
                        for (int i = 0; i < blockSize && (x + i) < width; i++) {
                            int rgb = (rand.nextInt(256) << 16) | (rand.nextInt(256) << 8) | rand.nextInt(256);
                            image.setRGB(i, j, rgb);
                        }
                    }
                    writer.write(null, new IIOImage(image, null, null), param);
                }
            }
        } finally {
            writer.dispose();
        }

        System.out.println("Saved into : " + outputFile.getAbsolutePath());
    }
}
