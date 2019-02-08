package com.cauldron.bodyconquest.entities;

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

public class Base extends Troop {

  protected int healthBacteriaBase = 100;
  protected int healthMonsterBase = 75;
  protected int healthVirusBase = 70;

  protected int damageBacteriaBase = 3;
  protected int damageMonsterBase = 8;
  protected int damageVirusBase = 10;

  private UnitType diseaseType;

  protected Animation<TextureRegion> imageBase;
  // in order to see animation in rendering need to pass
  //  animation.getkeyframe(delta)
  private Texture texture;
  public Base(EncounterScreen screen, PlayerType playerType) {
    this.screen = screen;
    this.playerType = playerType;
    init();
  }

  public Base(PlayerType playerType, UnitType diseaseType) {
    this.diseaseType = diseaseType;
    this.playerType = playerType;
    this.attackable = true;
    this.moving = false;
    this.attacking = false;
    // this.range = 5 (dont know which value to put
    selectTexture();

//    boundsBase = new Rectangle();
//    boundsBase.x =;
//    boundsBase.y =;
//    boundsBase.height=;
//    boundsBase.width=; do we need it for collisions detection

    setInitialHealth();
    setDamage();

  }
  private void init() {
    setSize(150, 150);
    if (playerType == PlayerType.BOT_PLAYER) {
      setPosition(screen.getMap().getRight() - getWidth(), screen.getMap().getY());
       texture = new Texture("core/assets/Base (Green).png");
    } else if (playerType == PlayerType.TOP_PLAYER) {
      setPosition(screen.getMap().getX(), screen.getMap().getTop() - getHeight());
      texture = new Texture("core/assets/Base (Yellow).png");
    }

    maxHealth = health = 800;
    attackable = true;
    sprite = new Image(texture);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Color color = getColor();
    batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
    batch.draw(texture, getX(), getY(), getWidth(), getHeight());
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }
  public void changeAttackingMode() {
    this.attacking = true;
  }

  public void setInitialHealth() {

    if (diseaseType == UnitType.BACTERIA) {
      this.health = healthBacteriaBase;
    } else if (diseaseType == UnitType.MONSTER) {
      this.health = healthMonsterBase;
    } else if (diseaseType == UnitType.VIRUS) {
      this.health = healthVirusBase;
    }
  }

  public void setDamage() {

    if (diseaseType == UnitType.BACTERIA) {
      this.damage = damageBacteriaBase;
    } else if (diseaseType == UnitType.MONSTER) {
      this.damage = damageMonsterBase;
    } else if (diseaseType == UnitType.VIRUS) {
      this.damage = damageVirusBase;
    }
  }

  public void selectTexture() {

    if (diseaseType == Troop.UnitType.BACTERIA) {
      this.imageBase =
          GifDecoder.loadGIFAnimation(PlayMode.LOOP, Gdx.files.internal("core/assets/castle1.gif").read());
    } else if (diseaseType == UnitType.MONSTER) {
      this.imageBase =
          GifDecoder.loadGIFAnimation(PlayMode.LOOP, Gdx.files.internal("throbber.gif").read());
    } else if (diseaseType == UnitType.VIRUS) {
      this.imageBase =
          GifDecoder.loadGIFAnimation(PlayMode.LOOP, Gdx.files.internal("throbber.gif").read());
    }
  }

  public boolean conquered() {
    return isDead();
  }

  // to use method above we need to decide to use rectangles ( easy to spot collision)

//  public boolean checkCollision(Base base, MapObject object){
//    if(object.bound.overlaps(base.boundsBase))return true;
//
//    return false;
//  }
}
