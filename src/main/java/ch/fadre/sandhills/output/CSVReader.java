package ch.fadre.sandhills.output;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public int[][] parseFile(File file) throws IOException {
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
