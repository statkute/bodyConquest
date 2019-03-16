package main.com.bodyconquest.constants;

import main.com.bodyconquest.entities.Troops.Bases.InfluenzaBase;
import main.com.bodyconquest.entities.Troops.Bases.MeaslesBase;
import main.com.bodyconquest.entities.Troops.Bases.RotavirusBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseTypeTest {

    @Test
    public void getAssociatedClass() {
        assertEquals(BaseType.INFLUENZA_BASE.getAssociatedClass(), InfluenzaBase.class);
        assertEquals(BaseType.MEASLES_BASE.getAssociatedClass(), MeaslesBase.class);
        assertEquals(BaseType.ROTAVIRUS_BASE.getAssociatedClass(), RotavirusBase.class);
    }
}