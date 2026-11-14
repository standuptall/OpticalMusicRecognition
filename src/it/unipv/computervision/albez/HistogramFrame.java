package it.unipv.computervision.albez;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class HistogramFrame extends JFrame{
    private final JLabel jlabel;


    public HistogramFrame(int[] histogram, String title) {
        int width = 640;
        int height = 480;

        int maxFrequency = Arrays.stream(histogram).max().orElse(1);
        if (maxFrequency == 0) maxFrequency = 1; // Evita divisione per zero

        double vertRatio = (double) height / maxFrequency;
        double horizRatio = (double) width / histogram.length; // 256 bin, non 255

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // 1. Riempie tutto lo sfondo di bianco (fillRect, non drawRect)
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 2. Disegna le barre nere dal basso verso l'alto
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) (vertRatio * histogram[i]);
            int x1 = (int) (horizRatio * i);
            int x2 = (int) (horizRatio * (i + 1));
            int barWidth = Math.max(1, x2 - x1);

            // Parte da (height - barHeight) fino al fondo (height)
            graphics.fillRect(x1, height - barHeight, barWidth, barHeight);
        }

        graphics.dispose(); // Rilascia le risorse grafiche

        jlabel = new JLabel(new ImageIcon(image));
        initFrame(title);
    }

    private void initFrame(String title) {
        setTitle(title);
        getContentPane().add(jlabel);
        pack();
        setDefaultCloseOperation(defaultCloseOperation);
        setVisible(true);
    }

    public void setImage(Image img) {
        jlabel.setIcon(new ImageIcon(img));
    }

    public void setImage(String imageName) {
        jlabel.setIcon(new ImageIcon(imageName));
        pack();
        setTitle(imageName);
    }

    public Image getImage() {
        return ((ImageIcon) jlabel.getIcon()).getImage();
    }

    static int defaultCloseOperation = EXIT_ON_CLOSE;

    public static void main(String[] imageFileNames) {
        for (int i = 0; i < imageFileNames.length; i++) {
            new ImageFrame(imageFileNames[i]);
        }
    }
}
