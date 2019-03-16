package main.com.bodyconquest.entities.abilities;

import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.gamestates.EncounterState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RigorMortisTest {
    RigorMortis rm;

    @Before
    public void init(){
        rm = new RigorMortis();
    }

    @Test
    public void cast() throws Exception{
    }

    @Test
    public void getSugarCost() {
        assertEquals(rm.getSugarCost(), 60);
    }

    @Test
    public void getLipidCost() {
        assertEquals(rm.getLipidCost(), 20);
    }

    @Test
    public void getProteinCost() {
        assertEquals(rm.getProteinCost(), 0);
    }

    @Test
    public void getPortraitLocation() {
    }

    @Test
    public void damageAreaPath() {
    }
}