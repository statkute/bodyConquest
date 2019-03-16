package main.com.bodyconquest.constants;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectileTypeTest {

    @Test
    public void getMapObjectType() {
        assertEquals(ProjectileType.FLU_PROJECTILE.getMapObjectType(), ProjectileType.FLU_PROJECTILE);
    }

    @Test
    public void isProjectileType() {
        assertTrue(ProjectileType.isProjectileType(ProjectileType.FLU_PROJECTILE));
    }
}