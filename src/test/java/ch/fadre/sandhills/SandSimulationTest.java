package ch.fadre.sandhills;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class SandSimulationTest {

    private int[][] testGrid;
    private SandSimulation sandSimulation;

    @Before
    public void setUp() throws Exception {
        sandSimulation = new SandSimulation(3, 3, 1, false);
        testGrid = new int[3][3];
    }

    @Test
    public void testMoveToNeighbors_overflow() throws Exception {
        testGrid[1][1] = 4;

        sandSimulation.moveToNeighborsIfNecessary(1,1,testGrid);

        assertThat(testGrid[0][1], is(1));
        assertThat(testGrid[1][0], is(1));
        assertThat(testGrid[1][2], is(1));
        assertThat(testGrid[2][1], is(1));
        assertThat(testGrid[1][1], is(0));
    }

    @Test
    public void testMoveToNeighbors_NoOverflow1() throws Exception {
        for (short i = 0; i < 4; i++) {
            testGrid[1][1] = i;
            sandSimulation.moveToNeighborsIfNecessary(1,1,testGrid);
            assertThat(testGrid[0][1], is(0));
            assertThat(testGrid[1][0], is(0));
            assertThat(testGrid[1][2], is(0));
            assertThat(testGrid[2][1], is(0));
            assertThat(testGrid[1][1], is((int)i));
        }
    }

    @Test
    public void testIsBalanced_unBalanced() throws Exception {
        testGrid[1][1] = 4;

        int[] bounds = {0, 2, 0, 2};
        assertFalse(sandSimulation.isBalanced(testGrid));
    }

    @Test
    public void testIsBalanced_() throws Exception {
        testGrid[1][1] = 3;

        int[] bounds = {0, 2, 0, 2};

        assertTrue(sandSimulation.isBalanced(testGrid));
    }



}