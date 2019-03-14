package main.com.bodyconquest.entities.Troops.Bases;

import main.com.bodyconquest.entities.Troops.Troop;
import main.com.bodyconquest.constants.BaseType;
import main.com.bodyconquest.constants.Assets;

public class InfluenzaBase extends Base {

    public InfluenzaBase(Assets.PlayerType pt){
        super(pt);
        init();
    }

    private void init(){
        this.health = 1000;
        this.damage = 3;
        this.range = 130;
        this.maxHealth = health;
        mapObjectType = BaseType.INFLUENZA_BASE;
    }

    @Override
    public void attack(Troop troop) {
        super.attack(troop);
    }

    @Override
    public void update() {

    }

    @Override
    public String getPortraitLocation() {
        return null; // Shouldn't really be spawned
    }
}
