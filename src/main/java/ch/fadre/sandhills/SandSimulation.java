package ch.fadre.sandhills;


import ch.fadre.sandhills.output.ImageWriter;

import java.io.IOException;

class SandSimulation {

    private static final int MAX_SIZE = 4;

    private final long iterationCount;
    private final int[][] grid;
    private final int width;
    private final int height;

    private long moveOperations = 0;

    private String simulationName;
    private boolean stepWiseSimulation;

    private Bounds currentBounds;

    SandSimulation(int width, int height, long iterationCount, boolean stepWiseSimulation, String simulationName) {
        this.iterationCount = iterationCount;
        this.width = width;
        this.height = height;
        this.stepWiseSimulation = stepWiseSimulation;
        this.simulationName = simulationName;
        grid = new int[height][width];
    }

    int[][] performSimulation() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        int maxIterations = 0;
        currentBounds = new Bounds((height / 2)-1, (height / 2) +1, (width / 2)-1, (width / 2)+1);
        if (stepWiseSimulation) {
            performStepWiseSimulation(maxIterations);
        } else {
            grid[height / 2][width / 2] = (int) iterationCount;
            redistribute(grid);
        }
        System.out.println("Simulation took:" + ((System.currentTimeMillis() - start) / 1000.0) + " s for " + moveOperations + " total move operations");
        return grid;
    }

    private void performStepWiseSimulation(int maxIterations) throws IOException, InterruptedException {
        long lastIterationTimestamp = System.currentTimeMillis();
        for (long i = 0; i < iterationCount; i++) {
            grid[height / 2][width / 2] = (short) (grid[height / 2][width / 2] + 1);
            if (i % 1000 == 0) {
                lastIterationTimestamp = logProgress(i, maxIterations, lastIterationTimestamp);
                maxIterations = 0;
            }
            if (i % 10000 == 0) {
                ImageWriter imageWriter = new ImageWriter();
                imageWriter.writeToFile(imageWriter.generateImage(grid, width, height), simulationName + "-Step" + i);
            }

            int iterations = redistribute(grid);
            maxIterations = Math.max(maxIterations, iterations);
        }
    }

    private long logProgress(long iteration, int maxIterations, long lastIt) {
        String iterationString = "Iteration " + iteration + " of " + iterationCount + " (" + String.format("%.2f", iteration / (double) iterationCount * 100) + "%)";
        String boundString = " Bounds: " + currentBounds.getTop() + " " + currentBounds.getBottom() + " " + currentBounds.getLeft() + " " + currentBounds.getRight();
        long now = System.currentTimeMillis();
        String timeStr = (now - lastIt) + " ms";
        System.out.println(iterationString + boundString + " MaxIterations: " + maxIterations + " StepTime:" + timeStr +" MoveOperations: " + moveOperations);
        return now;
    }


    private int redistribute(int[][] boxes) {
        int redistributeIterations = 0;

        while (!isBalanced(boxes, currentBounds)) {
            for (int i = currentBounds.getTop(); i <= currentBounds.getBottom(); i++) {
                for (int j = currentBounds.getLeft(); j <= currentBounds.getRight(); j++) {
                    moveToNeighborsIfNecessary(i, j, boxes, currentBounds);
                }
            }
            redistributeIterations++;
        }
        return redistributeIterations;
    }

    boolean isBalanced(int[][] boxes, Bounds currentBounds) {
        int top = currentBounds.getTop();
        int bottom = currentBounds.getBottom();
        int left = currentBounds.getLeft();
        int right = currentBounds.getRight();
        for (int i = top; i < bottom; i++) {
            for (int j = left; j < right; j++) {
                if (boxes[i][j] >= MAX_SIZE) {
                    return false;
                }
            }
        }
        return true;
    }

    void moveToNeighborsIfNecessary(int i, int j, int[][] boxes, Bounds currentBounds) {
        if (boxes[i][j] < MAX_SIZE) {
            return;
        }
        moveOperations++;
        int newTop = i - 1;
        if (newTop >= 0) {
            boxes[newTop][j] += 1;
            currentBounds.decreaseTopIfNecessary(newTop);
        }
        int newBottom = i + 1;
        if (newBottom < height) {
            boxes[newBottom][j] += 1;
            currentBounds.increaseBottomIfNecessary(newBottom);
        }
        int newLeft = j - 1;
        if (newLeft >= 0) {
            boxes[i][newLeft] += 1;
            currentBounds.decreaseLeftIfNecessary(newLeft);
        }
        int newRight = j + 1;
        if (newRight < width) {
            boxes[i][newRight] += 1;
            currentBounds.increaseRightIfNecessary(newRight);
        }
        boxes[i][j] -= 4;
    }

}
