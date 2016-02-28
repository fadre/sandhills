package ch.fadre.sandhills.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVWriter {

    public void writeToFile(int[][] grid, String prefix)  {
        String fileName = prefix + System.currentTimeMillis() + ".csv";
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){
            for (int[] rows : grid) {
                String line = IntStream.of(rows).boxed().map(String::valueOf).collect(Collectors.joining(";"));
                bufferedWriter.write(line +"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
