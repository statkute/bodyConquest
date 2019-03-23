package main.com.bodyconquest.constants;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectileTypeTest {

    @Test
    public void getMapObjectType() {
        assertEquals(ProjectileType.VIRUS_PROJECTILE.getMapObjectType(), ProjectileType.VIRUS_PROJECTILE);
    }

    @Test
    public void isProjectileType() {
        assertTrue(ProjectileType.isProjectileType(ProjectileType.VIRUS_PROJECTILE));
    }
}