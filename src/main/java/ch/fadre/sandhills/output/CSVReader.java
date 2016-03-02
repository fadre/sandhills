package ch.fadre.sandhills.output;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {

    public Map<Integer, Color> parseColorConfig(File configFile) throws IOException {
        Reader in = new FileReader(configFile);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').withHeader("Level","Red","Green","Blue").parse(in);
        HashMap<Integer, Color> colorConfig = new HashMap<>();
        int i= 0;
        for (CSVRecord record : records) {
            i++;
            if(i == 1){
                continue;
            }
            Color color = getColorFromRecord(record);
            int level = Integer.parseInt(record.get("Level"));
            colorConfig.put(level, color);
        }
        return colorConfig;
    }

    private Color getColorFromRecord(CSVRecord record) {
        int red = Integer.parseInt(record.get("Red"));
        int green = Integer.parseInt(record.get("Green"));
        int blue = Integer.parseInt(record.get("Blue"));
        return new Color(red, green, blue);
    }

    public int[][] parseDataFile(File file) throws IOException {
        Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').parse(in);
        List<List<Integer>> result = new ArrayList<>();
        for (CSVRecord record : records) {
            List<Integer> row = new ArrayList<>();
            for (int i = 0; i < record.size(); i++) {
                row.add(Integer.parseInt(record.get(i)));
            }
            result.add(row);
        }
        return convertToGrid(result);
    }

    private int[][] convertToGrid(List<List<Integer>> result) {
        int[][] grid = new int[result.size()][result.get(0).size()];
        for (int i = 0; i < result.size(); i++) {
            List<Integer> row = result.get(i);
            for (int j = 0; j < row.size(); j++) {
                grid[i][j] = row.get(j);
            }
        }
        return grid;
    }
}
