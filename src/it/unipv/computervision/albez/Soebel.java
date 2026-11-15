package it.unipv.computervision.albez;

import java.awt.image.BufferedImage;

public class Soebel {
    private final BufferedImage image;
    int[][] soebelX = new int[3][3];
    int[][] soebelY = new int[3][3];

    public Soebel(BufferedImage image){
        this.image = image;

        soebelX[0][0] = -1;
        soebelX[0][1] = 0;
        soebelX[0][2] = 1;
        soebelX[1][0] = -2;
        soebelX[1][1] = 0;
        soebelX[1][2] = 2;
        soebelX[2][0] = -1;
        soebelX[2][1] = 0;
        soebelX[2][2] = 1;

        soebelY[0][0] = 1;
        soebelY[0][1] = 2;
        soebelY[0][2] = 1;
        soebelY[1][0] = 0;
        soebelY[1][1] = 0;
        soebelY[1][2] = 0;
        soebelY[2][0] = -1;
        soebelY[2][1] = -2;
        soebelY[2][2] = -1;
    }
    public BufferedImage applyModule(){
        try {
            Convolution convolution = new Convolution(image);
            int[] rgbx = convolution.apply(soebelX);
            int[] rgby = convolution.apply(soebelY);

            for (int i = 0; i < rgbx.length; i++) {
                int grayX = (rgbx[i] >> 16) & 0xFF;
                int grayY = (rgby[i] >> 16) & 0xFF;
                int newVal = (int) Math.sqrt(grayX * grayX + grayY * grayY);
                rgbx[i] = (0xFF << 24) | (newVal << 16) | (newVal << 8) | newVal;
            }
            var merged = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            merged.setRGB(0, 0, image.getWidth(), image.getHeight(), rgbx, 0, image.getWidth());
            return merged;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
