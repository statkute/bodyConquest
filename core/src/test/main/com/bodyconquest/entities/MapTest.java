package main.com.bodyconquest.entities;

import main.com.bodyconquest.constants.BaseType;
import main.com.bodyconquest.constants.Organ;
import main.com.bodyconquest.constants.PlayerType;
import main.com.bodyconquest.entities.Troops.Bases.InfluenzaBase;
import main.com.bodyconquest.entities.Troops.Bases.MeaslesBase;
import main.com.bodyconquest.entities.Troops.Bases.RotavirusBase;
import main.com.bodyconquest.handlers.AnimationWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
@RunWith(Parameterized.class)
public class MapTest {

    Organ organ;
    String path;
    int cols;
    int rows;
    int points;
    Map map;

    public MapTest(Organ organ, String path, int cols, int rows, int points){
        this.organ = organ;
        this.path = path;
        this.cols = cols;
        this.rows = rows;
        this.points = points;
        map = new Map(organ, true);
    }

    @Test
    public void testPaths(){
        assertEquals(map.getPath(), path);
    }

    @Test
    public void testCols(){
        assertEquals(map.getFrameCols(), cols);
    }

    @Test
    public void testRows(){
        assertEquals(map.getFrameRows(), rows);
    }

    @Test
    public void testPoints(){
        assertEquals(map.getPoints(), points);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData(){
        Object[][] data = new Object[][]{
                {Organ.LUNGS, "core/assets/map_lungs_ss.png", 4, 5, 30},
                {Organ.EYES, "core/assets/map_eyes_ss.png", 2, 11, 20},
                {Organ.HEART, "core/assets/map_heart_ss.png", 11, 1, 30},
                {Organ.INTESTINES, "core/assets/map_brain_ss.png", 4, 5, 20},
                {Organ.BRAIN, "core/assets/map_brain_ss.png", 4, 5, 40},
                {Organ.TEETH, "core/assets/map_teeth_ss.png", 5, 5, 10}
        };
        return Arrays.asList(data);
    }

}