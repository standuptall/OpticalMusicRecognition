package it.unipv.computervision.albez;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.util.Arrays;

public class Binarization {

    public static BufferedImage getGrayImage(BufferedImage image){
        int[] rgb = image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth());
        int[] ret = new int[image.getWidth()*image.getHeight()];
        for(int i =0;i<rgb.length;i++) {
            int value = rgb[i];
            int a = (value >> 24) & 0xFF;
            int r = (value >> 16) & 0xFF;
            int g = (value >> 8) & 0xFF;
            int b = value & 0xFF;

            int gray = (int) (0.2126 * r + 0.7152 * g + 0.0722 * b);
            // Ricompone il pixel in formato ARGB per setRGB
            ret[i] = (a << 24) | (gray << 16) | (gray << 8) | gray;
        }
        var buffered = new BufferedImage( image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        buffered.setRGB(0,0,image.getWidth(),image.getHeight(),ret,0,image.getWidth());
        return buffered;
    }

    public static BinarizationInfo getBinaryImage(BufferedImage image,int threshold){int width = image.getWidth();
        int height = image.getHeight();
        int[] rgb = image.getRGB(0, 0, width, height, null, 0, width);
        int[] grayValues = new int[rgb.length];
        int[] histogram = new int[256];

        // 1. Calcola i livelli di grigio e popola l'istogramma
        for (int i = 0; i < rgb.length; i++) {
            int gray = (rgb[i] >> 16) & 0xFF;
            grayValues[i] = gray;
            histogram[gray]++;
        }

        // 4. Applica la soglia usando i colori ARGB Bianco (0xFFFFFFFF) e Nero (0xFF000000)
        int[] newMap = new int[rgb.length];
        for (int i = 0; i < grayValues.length; i++) {
            if (grayValues[i] < threshold) {
                newMap[i] = 0xFF000000; // Nero opaco
            } else {
                newMap[i] = 0xFFFFFFFF; // Bianco opaco
            }
        }

        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        buffered.setRGB(0, 0, width, height, newMap, 0, width);
        var binfo = new BinarizationInfo();
        binfo.BinaryImage = buffered;
        binfo.Histogram  =  histogram;
        return binfo;
    }

    public static class BinarizationInfo
    {
        public BufferedImage BinaryImage;
        public int[] Histogram;
    }
}
