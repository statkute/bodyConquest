package com.cauldron.bodyconquest.entities.Troops.Bases;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.cauldron.bodyconquest.entities.Troops.Troop;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.PlayerType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.cauldron.bodyconquest.handlers.GifDecoder;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import java.util.ArrayList;

public abstract class Base extends Troop {


    protected Animation<TextureRegion> imageBase;


    public Base(PlayerType playerType) {
      this.playerType = playerType;
      this.attackable = true;
      this.moving = false;
      this.attacking = false;

      // this.range = 5 (dont know which value to put


    }

//  private void init() {
//    setSize(150, 150);
//    if (playerType == PlayerType.BOT_PLAYER) {
//      setPosition(screen.getMap().getRight() - getWidth(), screen.getMap().getY());
//       texture = new Texture("core/assets/Base (Green).png");
//    } else if (playerType == PlayerType.TOP_PLAYER) {
//      setPosition(screen.getMap().getX(), screen.getMap().getTop() - getHeight());
//      texture = new Texture("core/assets/Base (Yellow).png");
//    }
//
//    maxHealth = health = 800;
//    attackable = true;
//    sprite = new Image(texture);
//  }









  // to use method above we need to decide to use rectangles ( easy to spot collision)


}
