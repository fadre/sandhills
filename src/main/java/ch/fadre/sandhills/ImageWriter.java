package ch.fadre.sandhills;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {

    public BufferedImage generateImage(short[][] grid, int width, int height) throws IOException, InterruptedException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < grid.length; i++) {
            short[] row = grid[i];
            for (int j = 0; j < row.length; j++) {
                bufferedImage.setRGB(i, j, getColor(row[j]).getRGB());
            }
        }
        return bufferedImage;
    }

    public void displayInFrame(BufferedImage bufferedImage) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(1200, 1000);
        jFrame.getContentPane().setLayout(new BorderLayout());
        jFrame.getContentPane().add(new JLabel(new ImageIcon(bufferedImage)));
        jFrame.setVisible(true);
    }

    public void writeToFile(BufferedImage bufferedImage, String prefix) throws IOException {
        String fileName = prefix + System.currentTimeMillis() + ".png";
        ImageIO.write(bufferedImage, "png", new File(fileName));
    }

    private static Color getColor(short i) {
        switch (i) {
            case 0:
                return new Color(39, 111, 161);
            case 1:
                return new Color(26, 151, 82);
            case 2:
                return new Color(224, 143, 18);
            case 3:
                return new Color(176, 52, 39);
            default:
                System.out.println("This should never happen :)");
                return new Color(39, 111, 161);
        }
    }
}
