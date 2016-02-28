package ch.fadre.sandhills;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static final int width = 750;
    public static final int height = 750;
    public static final int ITERATIONS = 1000_000;

    public static void main(String[] args) throws IOException, InterruptedException {

        SandSimulation sandSimulation = new SandSimulation(width, height, ITERATIONS);
        short[][] result = sandSimulation.performSimulation();

        ImageWriter imageWriter = new ImageWriter();
        BufferedImage bufferedImage = imageWriter.generateImage(result, width, height);
        imageWriter.writeToFile(bufferedImage, "test");
        imageWriter.displayInFrame(bufferedImage);
    }


}
