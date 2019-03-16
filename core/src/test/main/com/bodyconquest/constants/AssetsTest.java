package main.com.bodyconquest.constants;

import org.junit.Test;

import static org.junit.Assert.*;

public class AssetsTest {

    @Test
    public void bacteriaTestUnitType(){
        assertEquals(Assets.UnitType.BACTERIA.getEncoded(), "BAC");
    }

    @Test
    public void fluTestUnitType(){
        assertEquals(Assets.UnitType.FLU.getEncoded(), "FLU");
    }

    @Test
    public void virusTestUnitType(){
        assertEquals(Assets.UnitType.VIRUS.getEncoded(), "VIR");
    }

    @Test
    public void bacteriaDecodeTest(){
        assertEquals(Assets.UnitType.decode("BAC"), Assets.UnitType.BACTERIA);
    }

    @Test
    public void fluDecodeTest(){
        assertEquals(Assets.UnitType.decode("FLU"), Assets.UnitType.FLU);
    }

    @Test
    public void virusDecodeTest(){
        assertEquals(Assets.UnitType.decode("VIR"), Assets.UnitType.VIRUS);
    }

    @Test
    public void bacteriaEcodeTest(){
        assertEquals(Assets.UnitType.BACTERIA.getEncoded(), "BAC");
    }

    @Test
    public void fluEcodeTest(){
        assertEquals(Assets.UnitType.FLU.getEncoded(), "FLU");
    }

    @Test
    public void virusEcodeTest(){
        assertEquals(Assets.UnitType.VIRUS.getEncoded(), "VIR");
    }

    @Test
    public void topPlayerTypeTest(){
        assertEquals(Assets.PlayerType.PLAYER_TOP.getEncoded(), "PT");
    }

    @Test
    public void botPlayerTypeTest(){
        assertEquals(Assets.PlayerType.PLAYER_BOTTOM.getEncoded(), "PB");
    }

    @Test
    public void aiPlayerTypeTest(){
        assertEquals(Assets.PlayerType.AI.getEncoded(), "AI");
    }

    @Test
    public void topPlayerTypeDecodeTest(){
        assertEquals(Assets.PlayerType.decode("PT"), Assets.PlayerType.PLAYER_TOP);
    }

    @Test
    public void botPlayerTypeDecodeTest(){
        assertEquals(Assets.PlayerType.decode("PB"), Assets.PlayerType.PLAYER_BOTTOM);
    }

    @Test
    public void aiPlayerTypeDecodeTest(){
        assertEquals(Assets.PlayerType.decode("AI"), Assets.PlayerType.AI);
    }

}