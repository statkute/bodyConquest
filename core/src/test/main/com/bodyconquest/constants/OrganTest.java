package main.com.bodyconquest.constants;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class OrganTest {

    private Organ organ;
    private String enc;
    private int points;

    public OrganTest(Organ organ, String enc, int points){
        this.organ = organ;
        this.enc = enc;
        this.points = points;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testData(){
        Object[][] data = new Object[][]{
                {Organ.BRAIN,      "BRA", 40},
                {Organ.EYES,       "EYE", 20},
                {Organ.HEART,      "HRT", 30},
                {Organ.LUNGS,      "LNG", 30},
                {Organ.TEETH,      "TEE", 10},
                {Organ.INTESTINES, "INT", 20}};
        return Arrays.asList(data);
    }

    @Test
    public void decode() {
        assertEquals(Organ.decode(enc), organ);
    }

    @Test
    public void getOrganScore() {
        assertEquals(organ.getOrganScore(), points);
    }

    @Test
    public void getEncoded() {
        assertEquals(organ.getEncoded(), enc);
    }

    @Test
    public void getEncodedLength() {
        assertEquals(Organ.getEncodedLength(), 3);
    }
}