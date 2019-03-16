package main.com.bodyconquest.constants;

import main.com.bodyconquest.entities.abilities.RigorMortis;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbilityTypeTest {


    @Test
    public void getEncodedLength() {
        assertEquals(AbilityType.getEncodedLength(),3);
    }

    @Test
    public void decode() {
        assertEquals(AbilityType.decode("RGM"), AbilityType.RIGOR_MORTIS);
    }

    @Test
    public void getEncoded() {
        assertEquals(AbilityType.RIGOR_MORTIS.getEncoded(), "RGM");
    }

    @Test
    public void getAssociatedClass() {
        assertEquals(AbilityType.RIGOR_MORTIS.getAssociatedClass(), RigorMortis.class);
    }
}