package it.unipv.computervision.albez;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");
        String currentPath = null;
        try {
            currentPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println((currentPath));
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(currentPath+"\\avvoltoio.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var grayImage = Binarization.getGrayImage(image);
        var binaryImage = Binarization.getBinaryImage(grayImage,120);
        ImageFrame imageFrame = new ImageFrame(binaryImage.BinaryImage,"Avvoltoio");
        HistogramFrame histogramFrame = new HistogramFrame(binaryImage.Histogram,"Istogramma");

        Convolution convolution = new Convolution(grayImage);
        int[][] soebelX = new int[3][3];
        soebelX[0][0] = -1;
        soebelX[0][1] = 0;
        soebelX[0][2] = 1;
        soebelX[1][0] = -2;
        soebelX[1][1] = 0;
        soebelX[1][2] = 2;
        soebelX[2][0] = -1;
        soebelX[2][1] = 0;
        soebelX[2][2] = 1;



        int[][] soebelY = new int[3][3];
        soebelY[0][0] = 1;
        soebelY[0][1] = 2;
        soebelY[0][2] = 1;
        soebelY[1][0] = 0;
        soebelY[1][1] = 0;
        soebelY[1][2] = 0;
        soebelY[2][0] = -1;
        soebelY[2][1] = -2;
        soebelY[2][2] = -1;

        BufferedImage soebeledX = null;
        BufferedImage soebeledY = null;
        BufferedImage merged = null;
        try {
            soebeledX = convolution.apply(soebelX);
            soebeledY = convolution.apply(soebelY);
            var rgbx = soebeledX.getRGB(0, 0, soebeledX.getWidth(), soebeledX.getHeight(), null, 0, soebeledX.getWidth());
            var rgby = soebeledY.getRGB(0, 0, soebeledY.getWidth(), soebeledY.getHeight(), null, 0, soebeledY.getWidth());
            for(int i=0;i<rgbx.length;i++) {
                int grayX = (rgbx[i] >> 16) & 0xFF;
                int grayY = (rgby[i] >> 16) & 0xFF;
                int newVal = (int) Math.sqrt(grayX * grayX + grayY * grayY);
                rgbx[i] = (0xFF << 24) | (newVal << 16) | (newVal << 8) | newVal;
            }

            merged = new BufferedImage(soebeledX.getWidth(), soebeledX.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            merged.setRGB(0, 0, soebeledX.getWidth(), soebeledX.getHeight(), rgbx, 0, soebeledX.getWidth());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var imX = new ImageFrame(soebeledX,"SoebelX");
        var imY = new ImageFrame(soebeledY,"SoebelY");
        var mergedImage = new ImageFrame(merged,"Merged");

        var binaryImageForSoebel = Binarization.getBinaryImage(merged,120);
        var finalImage = new ImageFrame(binaryImageForSoebel.BinaryImage,"Finally");
        BufferedReader r = new BufferedReader(
                new InputStreamReader(System.in));

        try {
            String s = r.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}