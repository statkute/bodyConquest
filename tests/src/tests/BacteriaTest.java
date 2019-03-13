package tests;

import com.cauldron.bodyconquest.entities.Troops.Bacteria;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BacteriaTest {
    Bacteria bacteria = new Bacteria();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testHealth() throws Exception{
        assertEquals(bacteria.MAX_HEALTH, 100);
    }

    @Test
    public void testSugarCost() throws Exception{
        assertEquals(bacteria.SUGARS_COST, 10);

    }

    @Test
    public void testFatCost() throws Exception{
        assertEquals(bacteria.LIPIDS_COST, 20);

    }

    @Test
    public void testProteinCost() throws Exception{
        assertEquals(bacteria.PROTEINS_COST, 30);

    }

    @After
    public void tearDown() throws Exception {
    }
}