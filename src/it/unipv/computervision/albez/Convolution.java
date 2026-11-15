package it.unipv.computervision.albez;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Convolution {
    private final BufferedImage image;

    public Convolution(BufferedImage image){
        this.image = image;
    }
    /*
    * APplca la convoluzione ritornando l'array RGB
     */
    public int[] apply(int[][] matrix) throws Exception {
        var length = matrix.length;
        if (matrix[0].length!= length)
            throw new Exception("Il kernel deve essere quadrato!");
        var width  = image.getWidth();
        var height = image.getHeight();
        int[] rgb = image.getRGB(0, 0, width, height, null, 0, width);

        for (int i = 0; i < rgb.length; i++) {
            int[][] map = this.getMap(i,width,height,length,rgb);
            int newVal = applyKernel(map,matrix);
            rgb[i] = newVal;
        }
        return rgb;
    }

    private int[][] getMap(int i,int width,int height,int kernelLenght,int[] rgb) {
        var map = new int[kernelLenght][kernelLenght];

        int mezzoKernel = kernelLenght / 2;
        for(int m = 0;m<kernelLenght;m++)
            for(int n = 0;n<kernelLenght;n++) {
                //faccio controllo che non sono vicino ai bordi, in tal caso lascio pixel neri
                var toFind = getRelative(i, width, m, n);
                if (toFind >0 && toFind<rgb.length && m >0 && n > 0 )
                    map[m][n] = rgb[toFind];
            }
        return map;
    }
    private int getRelative(int i,int width,int relativeX,int relativeY){
        return i + relativeX + relativeY * width;
    }
    private int applyKernel(int[][] original,int[][] matrix){
        int product  = 0;
        for(int i =0;i< original.length;i++)
            for(int j=0;j< original.length;j++)
                product += original[i][j] * matrix[i][j];
        return product;
    }
}
