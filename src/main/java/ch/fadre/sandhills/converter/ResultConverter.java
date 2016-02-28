package ch.fadre.sandhills.converter;

import ch.fadre.sandhills.output.CSVReader;
import ch.fadre.sandhills.output.ImageWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ResultConverter {

    public static void main(String[] args) throws IOException, InterruptedException {
        File input = new File(args[0]);
        int[][] grid = new CSVReader().parseFile(input);

        ImageWriter imageWriter = new ImageWriter();
        BufferedImage bufferedImage = imageWriter.generateImage(grid, grid[0].length, grid.length);

        imageWriter.writeToFile(bufferedImage,input.getName());

    }
}
