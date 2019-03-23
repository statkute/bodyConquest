package main.com.bodyconquest.entities.projectiles;

import main.com.bodyconquest.entities.Troops.Bacteria;
import main.com.bodyconquest.entities.Troops.Troop;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FluProjectileTest {
    VirusProjectile fp;

    @Before
    public void init(){
        fp = new VirusProjectile(10,10,10,20,20);
    }

    @Test
    public void update() {
        fp.update();
        assertFalse(fp.getRemove());
    }

    @Test
    public void setRemoved(){
        fp.setRemove();
        assertTrue(fp.remove);
    }

    @Test
    public void getRemove(){
        fp.setRemove();
        assertTrue(fp.getRemove());
    }

    @Test
    public void hit(){
        Troop t = new Bacteria();
        fp.hit(t);
        assertTrue(fp.getRemove());
        assertEquals(t.getHealth(), t.getMaxHealth()-10);
    }
}