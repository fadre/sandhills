package ch.fadre.sandhills;


import ch.fadre.sandhills.output.ImageWriter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SandSimulation {

    private static final int MAX_SIZE = 4;

    private final long iterationCount;
    private final int[][] grid;
    private final int width;
    private final int height;

    private long moveOperations = 0;

    private String simulationName;
    private boolean stepWiseSimulation;

    private Bounds currentBounds;

    private List<DistributeEntry> toBalanceQueue = new LinkedList<>();

    public SandSimulation(int width, int height, long iterationCount, boolean stepWiseSimulation, String simulationName) {
        this.iterationCount = iterationCount;
        this.width = width;
        this.height = height;
        this.stepWiseSimulation = stepWiseSimulation;
        this.simulationName = simulationName;
        grid = new int[height][width];
    }

    public int[][] performSimulation() throws IOException, InterruptedException {
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
            if(grid[height / 2][width / 2] >= MAX_SIZE){
                toBalanceQueue.add(new DistributeEntry(height/2,width/2));
            }
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
            List<DistributeEntry> newEntries = new LinkedList<>();
            for (DistributeEntry distributeEntry : toBalanceQueue) {
                moveToNeighborsIfNecessary(distributeEntry.getX(), distributeEntry.getY(), boxes, currentBounds, newEntries);
            }
            toBalanceQueue =  toBalanceQueue.stream().filter(e -> boxes[e.getX()][e.getY()] >= MAX_SIZE).collect(Collectors.toCollection(LinkedList::new));
            toBalanceQueue.addAll(newEntries);
            redistributeIterations++;
        }

        return redistributeIterations;
    }

    boolean isBalanced(int[][] boxes, Bounds currentBounds) {
        return toBalanceQueue.isEmpty();
//        int top = currentBounds.getTop();
//        int bottom = currentBounds.getBottom();
//        int left = currentBounds.getLeft();
//        int right = currentBounds.getRight();
//        for (int i = top; i < bottom; i++) {
//            for (int j = left; j < right; j++) {
//                if (boxes[i][j] >= MAX_SIZE) {
//                    return false;
//                }
//            }
//        }
//        return true;
    }

    void moveToNeighborsIfNecessary(int i, int j, int[][] boxes, Bounds currentBounds, List<DistributeEntry> newEntries) {
        if (boxes[i][j] < MAX_SIZE) {
            return;
        }
        moveOperations++;
        int newTop = i - 1;
        if (newTop >= 0) {
            boxes[newTop][j] += 1;
            addIfLarger(newTop,j,boxes,newEntries);
        }
        int newBottom = i + 1;
        if (newBottom < height) {
            boxes[newBottom][j] += 1;
            addIfLarger(newBottom,j,boxes,newEntries);
        }
        int newLeft = j - 1;
        if (newLeft >= 0) {
            boxes[i][newLeft] += 1;
            addIfLarger(i,newLeft,boxes,newEntries);
        }
        int newRight = j + 1;
        if (newRight < width) {
            boxes[i][newRight] += 1;
            addIfLarger(i,newRight,boxes,newEntries);
        }
       boxes[i][j] -= 4;
    }

    private void addIfLarger(int i, int j, int[][] grid, List<DistributeEntry> toRedistribute){
        if(grid[i][j] >= MAX_SIZE){
            toRedistribute.add(new DistributeEntry(i,j));
        }
    }


}
