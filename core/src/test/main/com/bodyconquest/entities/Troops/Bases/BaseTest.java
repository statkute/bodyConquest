package main.com.bodyconquest.entities.Troops.Bases;

import main.com.bodyconquest.constants.Assets;
import main.com.bodyconquest.constants.BaseType;
import main.com.bodyconquest.constants.MapObjectType;
import main.com.bodyconquest.constants.Organ;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BaseTest {

    Base b;
    int health;
    int damage;
    int range;
    BaseType bt;

    public BaseTest(Base b, int health, int damage, int range, BaseType bt){
        this.b = b;
        this.health = health;
        this.damage = damage;
        this.range = range;
        this.bt = bt;
    }

    @Test
    public void health(){
        assertEquals(b.getHealth(), health);
    }

    @Test
    public void damage(){
        assertEquals(b.getDamage(), damage);
    }

    @Test
    public void maxHeealth(){
        assertEquals(b.getMaxHealth(), health);
    }

    @Test
    public void playerType(){
        assertEquals(b.getPlayerType(), Assets.PlayerType.PLAYER_BOTTOM);
    }

    @Test
    public void lane(){
        assertEquals(b.getLane(), Assets.Lane.ALL);
    }

    @Test
    public void speed(){
        assertEquals(b.getMovementSpeed(), 0, 0.1f);
    }

    @Test
    public void killPoints(){
        assertEquals(b.getKillingPoints(), 0);
    }

    @Test
    public void range(){
        assertEquals(b.getRange(), range);
    }

    @Test
    public void ras(){
        assertEquals(b.getMapObjectType(), bt);
    }


    @Parameterized.Parameters
    public static Collection<Object[]> testData(){
        Object[][] data = new Object[][]{
                {new InfluenzaBase(Assets.PlayerType.PLAYER_BOTTOM),1000, 3,  130, BaseType.INFLUENZA_BASE},
                {new MeaslesBase(Assets.PlayerType.PLAYER_BOTTOM),  75,   8,  0,   BaseType.MEASLES_BASE},
                {new RotavirusBase(Assets.PlayerType.PLAYER_BOTTOM),70,   10, 0,   BaseType.ROTAVIRUS_BASE}};
        return Arrays.asList(data);
    }

}