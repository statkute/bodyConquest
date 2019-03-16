package main.com.bodyconquest.entities.resources;

import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.GameType;
import main.com.bodyconquest.game_logic.Game;
import main.com.bodyconquest.networking.Server;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ResourcesTest {
    static Resources r;


    @BeforeClass
    public static void init() throws Exception{
        Game g = new Game(GameType.SINGLE_PLAYER);
        r = new Resources(g.getServer(), Assets.PlayerType.PLAYER_BOTTOM);
    }

    @Test
    public void canAfford() {
        assertTrue(r.canAfford(100,100,100));
        assertFalse(r.canAfford(1000,1000,1000));
    }

    @Test
    public void buy() throws Exception{
        r.buy(30,40,50);
        Field max = r.getClass().getDeclaredField("MAX_RESOURCE");
        max.setAccessible(true);
        assertEquals(r.getLipids() + 30, max.get(r));
        assertEquals(r.getSugars() + 40, max.get(r));
        assertEquals(r.getProteins() + 50, max.get(r));
    }

    @Test
    public void increaseLipidRegeneration() throws Exception{
        Field lr = r.getClass().getDeclaredField("regenerationLipids");
        lr.setAccessible(true);
        Object initial = lr;
        r.increaseLipidRegeneration(20);
        assertEquals(lr, initial);
    }

    @Test
    public void increaseSugarRegeneration() throws Exception{
        Field sr = r.getClass().getDeclaredField("regenerationSugars");
        sr.setAccessible(true);
        Object initial = sr;
        r.increaseSugarRegeneration(20);
        assertEquals(sr, initial);
    }

    @Test
    public void increaseProteinRegeneration() throws Exception{
        Field pr = r.getClass().getDeclaredField("regenerationLipids");
        pr.setAccessible(true);
        Object initial = pr;
        r.increaseLipidRegeneration(20);
        assertEquals(pr, initial);
    }

    @Test
    public void getLipids() {
        assertEquals(r.getLipids(), 70);
    }

    @Test
    public void getProteins() {
        assertEquals(r.getProteins(), 50);
    }

    @Test
    public void getSugars() {
        assertEquals(r.getSugars(), 60);
    }
}