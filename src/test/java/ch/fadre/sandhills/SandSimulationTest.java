package ch.fadre.sandhills;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class SandSimulationTest {

    private short[][] testGrid;
    private SandSimulation sandSimulation;

    @Before
    public void setUp() throws Exception {
        sandSimulation = new SandSimulation(3, 3, 1);
        testGrid = new short[3][3];
    }

    @Test
    public void testMoveToNeighbors_overflow() throws Exception {
        testGrid[1][1] = 4;

        sandSimulation.moveToNeighborsIfNecessary(1,1,testGrid);

        assertThat((int)testGrid[0][1], is(1));
        assertThat((int)testGrid[1][0], is(1));
        assertThat((int)testGrid[1][2], is(1));
        assertThat((int)testGrid[2][1], is(1));
        assertThat((int)testGrid[1][1], is(0));
    }

    @Test
    public void testMoveToNeighbors_NoOverflow1() throws Exception {
        for (short i = 0; i < 4; i++) {
            testGrid[1][1] = i;
            sandSimulation.moveToNeighborsIfNecessary(1,1,testGrid);
            assertThat((int)testGrid[0][1], is(0));
            assertThat((int)testGrid[1][0], is(0));
            assertThat((int)testGrid[1][2], is(0));
            assertThat((int)testGrid[2][1], is(0));
            assertThat((int)testGrid[1][1], is((int)i));
        }
    }



}