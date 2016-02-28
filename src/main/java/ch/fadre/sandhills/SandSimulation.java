package ch.fadre.sandhills;


public class SandSimulation {

    private final long iterationCount;
    private final short[][] grid;
    private final int width;
    private final int height;

    public static final int MAX_SIZE = 4;

    public SandSimulation(int width, int height, long iterationCount) {
        this.iterationCount = iterationCount;
        this.width = width;
        this.height = height;
        grid = new short[height][width];
    }

    public short[][] performSimulation(){
        long start = System.currentTimeMillis();
        int maxIterations = 0;
        long lastIt = start;

        for (long i = 0; i < iterationCount; i++) {
            grid[height / 2][width / 2] = (short) (grid[height / 2][width / 2] + 1);
            if (i % 1000 == 0) {
                lastIt = logProgress(grid, i, maxIterations, lastIt);
                maxIterations = 0;
            }

            int iterations = redistribute(grid,i );
            maxIterations = Math.max(maxIterations, iterations);
        }

        System.out.println("Took:" + (System.currentTimeMillis() - start) + " ms");
        return grid;
    }

    private long logProgress(short[][] boxes,long iteration, int maxIterations, long lastIt) {
        int[] bounds = getBounds(boxes, iteration);
        String iterationString = "Iteration " + iteration + " of " + iterationCount + " (" + String.format("%.2f", iteration / (double) iterationCount * 100) + "%)";
        String boundString = " Bounds: " + bounds[0] + " " + bounds[1] + " " + bounds[2] + " " + bounds[3];
        long now = System.currentTimeMillis();
        String timeStr = (now - lastIt) + " ms";
        System.out.println(iterationString + boundString + " MaxIterations: " + maxIterations + " Time:" + timeStr);
        return now;
    }


    private int redistribute(short[][] boxes, long numberOfElements) {
        int iterations = 0;
        boolean balanced = false;
        int[] bounds = getBounds(boxes, numberOfElements);
        int maxBoundOffset = getBoundOffset(numberOfElements);

        while (!balanced) {
            for (int i = bounds[2]; i <= bounds[3]; i++) {
                for (int j = bounds[0]; j <= bounds[1]; j++) {
                    moveToNeighborsIfNecessary(i, j, boxes);

                }
            }
            bounds[0] = Math.max(0, Math.max(bounds[0] - 1, width/2 - maxBoundOffset/2));
            bounds[1] = Math.min(width-1,Math.min(bounds[1] + 1, width/2 + maxBoundOffset/2));
            bounds[2] = Math.max(0, Math.max(bounds[2] - 1, height/2 - maxBoundOffset/2));
            bounds[3] = Math.min(height - 1, Math.min(bounds[3] + 1, height / 2 + maxBoundOffset/2));
            balanced = isBalanced(boxes);
            iterations++;
        }
        return iterations;
    }

    private static int getBoundOffset(long numberOfElements) {
        return (int)(Math.floor(Math.sqrt(numberOfElements))) + 1;
    }

    private int[] getBounds(short[][] boxes,long  nrOfElements) {
        int iMin = Integer.MAX_VALUE;
        int iMax = Integer.MIN_VALUE;
        int jMin = Integer.MAX_VALUE;
        int jMax = Integer.MIN_VALUE;

        int boundOffset = getBoundOffset(nrOfElements);
        int minI = Math.max((width / 2 - boundOffset / 2) - 2,0);
        int maxI = Math.min((width / 2 + boundOffset / 2) + 2,boxes.length);
        int minJ = Math.max((height / 2 - boundOffset / 2) - 2, 0);
        int maxJ = Math.min((height / 2 + boundOffset / 2) + 2,boxes[0].length);
        for (int i = minI; i < maxI; i++) {
            for (int j = minJ; j < maxJ; j++) {
                if (boxes[i][j] > 0) {
                    iMin = Math.min(i, iMin);
                    iMax = Math.max(i, iMax);
                    jMin = Math.min(j, jMin);
                    jMax = Math.max(j, jMax);
                }
            }
        }
        return new int[]{iMin, iMax, jMin, jMax};
    }

    private boolean isBalanced(short[][] boxes) {
        for (short[] row : boxes) {
            for (short value : row) {
                if (value >= MAX_SIZE) {
                    return false;
                }
            }
        }
        return true;
    }

    void moveToNeighborsIfNecessary(int i, int j, short[][] boxes) {
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
