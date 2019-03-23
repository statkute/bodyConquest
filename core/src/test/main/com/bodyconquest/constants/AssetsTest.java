package main.com.bodyconquest.constants;

import org.junit.Test;

import static org.junit.Assert.*;

public class AssetsTest {

    @Test
    public void bacteriaTestUnitType(){
        assertEquals(UnitType.BACTERIA.getEncoded(), "BAC");
    }

    @Test
    public void fluTestUnitType(){
        assertEquals(UnitType.VIRUS.getEncoded(), "VIR");
    }

    @Test
    public void virusTestUnitType(){
        assertEquals(UnitType.FUNGUS.getEncoded(), "FNG");
    }

    @Test
    public void bacteriaDecodeTest(){
        assertEquals(UnitType.decode("BAC"), UnitType.BACTERIA);
    }

    @Test
    public void fluDecodeTest(){
        assertEquals(UnitType.decode("VIR"), UnitType.VIRUS);
    }

    @Test
    public void virusDecodeTest(){
        assertEquals(UnitType.decode("FNG"), UnitType.FUNGUS);
    }

    @Test
    public void bacteriaEcodeTest(){
        assertEquals(UnitType.BACTERIA.getEncoded(), "BAC");
    }

    @Test
    public void fluEcodeTest(){
        assertEquals(UnitType.VIRUS.getEncoded(), "VIR");
    }

    @Test
    public void virusEcodeTest(){
        assertEquals(UnitType.FUNGUS.getEncoded(), "FNG");
    }

    @Test
    public void topPlayerTypeTest(){
        assertEquals(PlayerType.PLAYER_TOP.getEncoded(), "PT");
    }

    @Test
    public void botPlayerTypeTest(){
        assertEquals(PlayerType.PLAYER_BOTTOM.getEncoded(), "PB");
    }

    @Test
    public void aiPlayerTypeTest(){
        assertEquals(PlayerType.AI.getEncoded(), "AI");
    }

    @Test
    public void topPlayerTypeDecodeTest(){
        assertEquals(PlayerType.decode("PT"), PlayerType.PLAYER_TOP);
    }

    @Test
    public void botPlayerTypeDecodeTest(){
        assertEquals(PlayerType.decode("PB"), PlayerType.PLAYER_BOTTOM);
    }

    @Test
    public void aiPlayerTypeDecodeTest(){
        assertEquals(PlayerType.decode("AI"), PlayerType.AI);
    }

}