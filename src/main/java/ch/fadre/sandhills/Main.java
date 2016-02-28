package ch.fadre.sandhills;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static int width = 100;
    public static int height = 200;
    public static long iterationCount = 100_000;

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args == null || args.length == 0) {
            System.out.println("Warning: No parameters set, using defaults");
            showUsage();
        } else if (args.length == 3){
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
            iterationCount = Long.parseLong(args[2]);
            System.out.println("Starting simulation with grid (" + width+"w/" + height +"h) and " + iterationCount + " iterations...");
        } else {
            System.out.println("Too many parameters given! Using defaults");
            showUsage();
        }

        SandSimulation sandSimulation = new SandSimulation(width, height, iterationCount, true);
        int[][] result = sandSimulation.performSimulation();

        ImageWriter imageWriter = new ImageWriter();
        BufferedImage bufferedImage = imageWriter.generateImage(result, width, height);
        imageWriter.writeToFile(bufferedImage, "test");
        imageWriter.displayInFrame(bufferedImage);
    }

    private static void showUsage() {
        System.out.println("Usage: gradle run 500 500 10000");
    }


}
