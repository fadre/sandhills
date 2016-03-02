package ch.fadre.sandhills;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BoundsTest {

    private Bounds bounds;

    @Before
    public void setUp() throws Exception {
        bounds = new Bounds(5, 6, 7, 8);
    }

    @Test
    public void testDecreaseTopIfNecessary() throws Exception {
        bounds.decreaseTopIfNecessary(5);

        assertThat(bounds.getTop(), is(5));

        bounds.decreaseTopIfNecessary(3);

        assertThat(bounds.getTop(), is(3));
    }

    @Test
    public void testIncreaseBottomIfNecessary() throws Exception {

        bounds.increaseBottomIfNecessary(5);

        assertThat(bounds.getBottom(), is(6));

        bounds.increaseBottomIfNecessary(7);

        assertThat(bounds.getBottom(), is(7));
    }

    @Test
    public void testDecreaseLeftIfNecessary() throws Exception {
        bounds.decreaseLeftIfNecessary(8);

        assertThat(bounds.getLeft(), is(7));

        bounds.decreaseLeftIfNecessary(4);

        assertThat(bounds.getLeft(), is(4));
    }

    @Test
    public void testIncreaseRightIfNecessary() throws Exception {
        bounds.increaseRightIfNecessary(3);

        assertThat(bounds.getRight(), is(8));

        bounds.increaseRightIfNecessary(9);

        assertThat(bounds.getRight(), is(9));
    }
}