package ch.fadre.sandhills;


import java.io.IOException;

public class SandSimulation {

    private final long iterationCount;
    private final int[][] grid;
    private final int width;
    private final int height;

    public static final int MAX_SIZE = 4;
    private boolean stepWiseSimulation;

    public SandSimulation(int width, int height, long iterationCount, boolean stepWiseSimulation) {
        this.iterationCount = iterationCount;
        this.width = width;
        this.height = height;
        this.stepWiseSimulation = stepWiseSimulation;
        grid = new int[height][width];
    }

    public int[][] performSimulation() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int maxIterations = 0;
        if(stepWiseSimulation){
            performStepWiseSimulation(maxIterations);
        } else {
            grid[height / 2][width / 2] = (int)iterationCount;
            redistribute(grid, iterationCount);
        }
        System.out.println("Took:" + (System.currentTimeMillis() - start) + " ms");
        return grid;
    }

    private void performStepWiseSimulation(int maxIterations) throws IOException, InterruptedException {
        long lastIterationTimestamp = System.currentTimeMillis();

        for (long i = 0; i < iterationCount; i++) {
            grid[height / 2][width / 2] = (short) (grid[height / 2][width / 2] + 1);
            if (i % 1000 == 0) {
                lastIterationTimestamp = logProgress(grid, i, maxIterations, lastIterationTimestamp);
                maxIterations = 0;
            }
            if(i % 10000 == 0){
                ImageWriter imageWriter = new ImageWriter();
                imageWriter.writeToFile(imageWriter.generateImage(grid,width,height),"row");
            }

            int iterations = redistribute(grid,i );
            maxIterations = Math.max(maxIterations, iterations);
        }
    }

    private long logProgress(int[][] boxes,long iteration, int maxIterations, long lastIt) {
        int[] bounds = getBounds(boxes, iteration);
        String iterationString = "Iteration " + iteration + " of " + iterationCount + " (" + String.format("%.2f", iteration / (double) iterationCount * 100) + "%)";
        String boundString = " Bounds: " + bounds[0] + " " + bounds[1] + " " + bounds[2] + " " + bounds[3];
        long now = System.currentTimeMillis();
        String timeStr = (now - lastIt) + " ms";
        System.out.println(iterationString + boundString + " MaxIterations: " + maxIterations + " Time:" + timeStr);
        return now;
    }


    private int redistribute(int[][] boxes, long numberOfElements) {
        int redistributeIterations = 0;
        int[] bounds = getBounds(boxes, numberOfElements);
        int maxBoundOffset = getBoundOffset(numberOfElements);

        while (!isBalanced(boxes)) {
            for (int i = bounds[0]; i <= bounds[1]; i++) {
                for (int j = bounds[2]; j <= bounds[3]; j++) {
                    moveToNeighborsIfNecessary(i, j, boxes);
                }
            }
            bounds[0] = Math.max(0, Math.max(bounds[0] - 1, height/2 - maxBoundOffset/2));
            bounds[1] = Math.min(height-1,Math.min(bounds[1] + 1, height/2 + maxBoundOffset/2));
            bounds[2] = Math.max(0, Math.max(bounds[2] - 1, width/2 - maxBoundOffset/2));
            bounds[3] = Math.min(width - 1, Math.min(bounds[3] + 1, width / 2 + maxBoundOffset/2));
            redistributeIterations++;
        }
        return redistributeIterations;
    }

    private static int getBoundOffset(long numberOfElements) {
        return (int)(Math.floor(Math.sqrt(numberOfElements))) + 1;
    }

    private int[] getBounds(int[][] boxes,long  nrOfElements) {
        int boundOffset = getBoundOffset(nrOfElements);
        int minI = Math.max((width / 2 - boundOffset / 2) - 2,0);
        int maxI = Math.min((width / 2 + boundOffset / 2) + 2,boxes.length-1);
        int minJ = Math.max((height / 2 - boundOffset / 2) - 2, 0);
        int maxJ = Math.min((height / 2 + boundOffset / 2) + 2,boxes[0].length-1);
        return new int[]{Math.max(0, minI), Math.min(height-1, maxI), Math.max(minJ, 0), Math.min(maxJ, width-1)};
    }

    boolean isBalanced(int[][] boxes) {
        for (int[] row : boxes) {
            for (int value : row) {
                if (value >= MAX_SIZE) {
                    return false;
                }
            }
        }
        return true;
    }

    void moveToNeighborsIfNecessary(int i, int j, int[][] boxes) {
        if (boxes[i][j] < MAX_SIZE) {
            return;
        }
        if (i - 1 >= 0) {
            boxes[i - 1][j] += 1;
        }
        if (i + 1 < height) {
            boxes[i + 1][j] += 1;
        }
        if (j - 1 >= 0) {
            boxes[i][j - 1] += 1;
        }
        if (j + 1 < width) {
            boxes[i][j + 1] += 1;
        }
        boxes[i][j] -= 4;
    }

}
