package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.cauldron.bodyconquest.gamestates.EncounterScreen;
import com.cauldron.bodyconquest.gamestates.EncounterScreen.PlayerType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.cauldron.bodyconquest.handlers.GifDecoder;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Base extends Unit {

  protected Rectangle boundsBase;

  protected int healthBacteriaBase = 100;
  protected int healthMonsterBase = 75;
  protected int healthVirusBase = 70;

  protected int damageBacteriaBase = 3;
  protected int damageMonsterBase = 8;
  protected int damageVirusBase = 10;

  private PlayerType player;
  private UnitType diseaseType;

  protected Texture healthBackground;
  protected Texture healthForeground;
  protected Texture healthBorder;

  protected Animation<TextureRegion> imageBase;
  // in order to see animation in rendering need to pass
  //  animation.getkeyframe(delta)

  public Base(PlayerType playerType, UnitType diseaseType) {

    this.diseaseType = diseaseType;
    this.player = playerType;
    this.attackable = true;
    this.moving = false;
    this.attacking = false;
    // this.range = 5 (dont know which value to put
    selectTexture();

    this.healthBackground = new Texture(Gdx.files.internal("core/assets/healthBackground.png"));
    this.healthForeground = new Texture(Gdx.files.internal("core/assets/healthForeground.png"));
    this.healthBorder = new Texture(Gdx.files.internal("core/assets/healthBorder.png"));

//    boundsBase = new Rectangle();
//    boundsBase.x =;
//    boundsBase.y =;
//    boundsBase.height=;
//    boundsBase.width=; do we need it for collisions detection

    setInitialHealth();
    setDamage();
  }

  public void changeAttackingMode() {
    this.attacking = true;
  }

  public void setInitialHealth() {

    if (diseaseType == UnitType.BACTERIA) {
      this.health = healthBacteriaBase;
      maxHealth = healthBacteriaBase;
    } else if (diseaseType == UnitType.MONSTER) {
      this.health = healthMonsterBase;
      maxHealth = healthMonsterBase;
    } else if (diseaseType == UnitType.VIRUS) {
      this.health = healthVirusBase;
      maxHealth = healthVirusBase;
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

    if (diseaseType == UnitType.BACTERIA) {
      this.imageBase =
          GifDecoder.loadGIFAnimation(PlayMode.LOOP, Gdx.files.internal("core/assets/castle1.gif").read());
      System.out.println("Does it read bases image");
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

//  @Override
//  public void draw(Batch batch, float parentAlpha){
//
//
//  }

  // to use method above we need to decide to use rectangles ( easy to spot collision)

//  public boolean checkCollision(Base base, MapObject object){
//    if(object.bound.overlaps(base.boundsBase))return true;
//
//    return false;
//  }
}
