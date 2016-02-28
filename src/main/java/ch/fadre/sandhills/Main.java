package ch.fadre.sandhills;

import ch.fadre.sandhills.output.CSVWriter;
import ch.fadre.sandhills.output.ImageWriter;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static int width = 250;
    public static int height = 250;
    public static long iterationCount = 100_000;

    public static void main(String[] args) throws IOException, InterruptedException {
        parseArguments(args);

        String simulationName = createSimulationName();

        SandSimulation sandSimulation = new SandSimulation(width, height, iterationCount, true, simulationName);
        int[][] result = sandSimulation.performSimulation();

        CSVWriter csvWriter = new CSVWriter();
        csvWriter.writeToFile(result,simulationName);

        ImageWriter imageWriter = new ImageWriter();
        BufferedImage bufferedImage = imageWriter.generateImage(result, width, height);
        imageWriter.writeToFile(bufferedImage, simulationName+"-final");

        imageWriter.displayInFrame(bufferedImage);
    }

    private static void parseArguments(String[] args) {
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
    }

    private static String createSimulationName() {
        return "Simulation-" + width + "x" + height + "-It" + iterationCount+"-At"+System.currentTimeMillis()/1000;
    }

    private static void showUsage() {
        System.out.println("Usage: gradle run -Dexec.args=\"500 500 10000\"");
    }


}
