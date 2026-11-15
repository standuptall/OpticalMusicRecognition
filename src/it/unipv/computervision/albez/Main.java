package it.unipv.computervision.albez;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String currentPath = null;
        try {
            currentPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println((currentPath));
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(currentPath+"\\partitura.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var grayImage = Binarization.getGrayImage(image);
        var binaryImage = Binarization.getBinaryImage(grayImage,120);
        ImageFrame imageFrame = new ImageFrame(binaryImage.BinaryImage,"Avvoltoio"); //già solo con threshold va bene

        HistogramFrame histogramFrame = new HistogramFrame(binaryImage.Histogram,"Istogramma");

        var binaryImageForSoebel = Binarization.getBinaryImage(binaryImage.BinaryImage,120);
        HorizontalProjection horizontalProjection = new HorizontalProjection(binaryImageForSoebel.BinaryImage);
        var penti = horizontalProjection.getStaff();
//        var soebel = new Soebel(binaryImage.BinaryImage);
//        var mergedImage = soebel.applyModule();
//        var finalImage = new ImageFrame(binaryImageForSoebel.BinaryImage,"Finally");
        BufferedReader r = new BufferedReader(
                new InputStreamReader(System.in));

        try {
            String s = r.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}