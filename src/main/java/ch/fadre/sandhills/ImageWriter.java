package ch.fadre.sandhills;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageWriter {

    private Map<Integer, Color> colorConfig = new HashMap<>();

    public ImageWriter() {
        colorConfig.put(0, new Color(32, 85, 126));
        colorConfig.put(1, new Color(26, 151, 82));
        colorConfig.put(2, new Color(224, 143, 18));
        colorConfig.put(3, new Color(210, 43, 40));
    }

    public ImageWriter(Map<Integer, Color> colorConfig) {
        this.colorConfig = colorConfig;
    }

    public BufferedImage generateImage(int[][] grid, int width, int height) throws IOException, InterruptedException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < grid.length; i++) {
            int[] row = grid[i];
            for (int j = 0; j < row.length; j++) {
                bufferedImage.setRGB(j, i, getColor(row[j]).getRGB());
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
        String fileName = prefix  + ".png";
        File resultsDir = new File("results");
        if(!resultsDir.exists()){
            resultsDir.mkdir();
        }
        ImageIO.write(bufferedImage, "png", new File(resultsDir,fileName));
    }

    private Color getColor(int i) {
        Color color = colorConfig.get(i);
        if(color == null){
            System.out.println("Warning: color not found for value" + i);
        }
        return color;
    }
}
