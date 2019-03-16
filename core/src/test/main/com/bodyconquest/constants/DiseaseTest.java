package main.com.bodyconquest.constants;

import org.junit.Test;

import static org.junit.Assert.*;

public class DiseaseTest {

    @Test
    public void getEncoded() {
        assertEquals(Disease.INFLUENZA.getEncoded(),"FLU");
        assertEquals(Disease.MEASLES.getEncoded(),"MES");
        assertEquals(Disease.ROTAVIRUS.getEncoded(),"RVI");
    }

    @Test
    public void decode() {
        assertEquals(Disease.decode("FLU"), Disease.INFLUENZA);
        assertEquals(Disease.decode("MES"), Disease.MEASLES);
        assertEquals(Disease.decode("RVI"), Disease.ROTAVIRUS);
    }

    @Test
    public void getEncodedLength() {
        assertEquals(Disease.getEncodedLength(),3);
    }

    @Test
    public void getBaseType() {
        assertEquals(Disease.INFLUENZA.getBaseType(), BaseType.INFLUENZA_BASE);
        assertEquals(Disease.MEASLES.getBaseType(), BaseType.MEASLES_BASE);
        assertEquals(Disease.ROTAVIRUS.getBaseType(), BaseType.ROTAVIRUS_BASE);
    }

    @Test
    public void getSpawn1() {
    }

    @Test
    public void getSpawn2() {
    }

    @Test
    public void getSpawn3() {
    }

    @Test
    public void getSpawn4() {
    }
}