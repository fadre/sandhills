package ch.fadre.sandhills.converter;

import ch.fadre.sandhills.output.CSVReader;
import ch.fadre.sandhills.output.ImageWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;


public class ResultConverter {

    public static void main(String[] args) throws IOException, InterruptedException {
        if(args == null || args.length == 0){
            System.out.println("Usage: dataFile [colorConfigFile]");
            return;
        }
        File input = new File(args[0]);
        Map<Integer, Color> colorConfig = null;
        if(args.length == 2){
            colorConfig = new CSVReader().parseColorConfig(new File(args[1]));
        }

        int[][] grid = new CSVReader().parseDataFile(input);

        ImageWriter imageWriter = colorConfig == null ? new ImageWriter() : new ImageWriter(colorConfig);
        BufferedImage bufferedImage = imageWriter.generateImage(grid, grid[0].length, grid.length);

        imageWriter.writeToFile(bufferedImage,input.getName());
    }
}
