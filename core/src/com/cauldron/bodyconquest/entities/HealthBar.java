package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class HealthBar extends Actor {

  protected Pixmap pixmapRed;
  protected Pixmap pixmapGreenBeginning;
  protected Pixmap pixmapGreenEnding;
  protected TextureRegionDrawable drawable;
//  protected ProgressBar.ProgressBarStyle progressBarStyle;


  public int width = 20;
  public int height = 100;

  public HealthBar() {
    init();
  }

  public void init() {

    pixmapRed = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    pixmapRed.setColor(Color.RED);
    pixmapRed.fill();
    setDrawable(pixmapRed);
//    progressBarStyle = P
//    progressBarStyle.background = drawable;
    pixmapGreenBeginning = new Pixmap(0,height, Pixmap.Format.RGBA8888);
    initGreen(pixmapGreenBeginning);
//    progressBarStyle.knob = drawable;
    pixmapGreenEnding = new Pixmap(width,height, Pixmap.Format.RGBA8888);
    initGreen(pixmapGreenEnding);
//    progressBarStyle.knobBefore = drawable;
  }




  public void setDrawable(Pixmap pixmap){

      this.drawable = new TextureRegionDrawable(new Texture(pixmap));
      dispose(pixmap);
  }

  public void initGreen(Pixmap pixmap){

      pixmap.setColor(Color.GREEN);
      pixmap.fill();
      setDrawable(pixmap);

  }

  public void dispose(Pixmap pixmap){
      pixmap.dispose();

  }

//  public ProgressBar.ProgressBarStyle  getProgressBarStyle(){
//
//      return progressBarStyle;
//
//  }


    }


    //    private NinePatchDrawable loadingBarBackground;
//
//    private NinePatchDrawable loadingBar;
//
//    protected float progress;
//
//    public HealthBar(Base base) {
//        TextureAtlas skinAtlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
//        NinePatch loadingBarBackgroundPatch = new NinePatch(skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
//        NinePatch loadingBarPatch = new NinePatch(skinAtlas.findRegion("default-round-down"), 5, 5, 4, 4);
//        loadingBar = new NinePatchDrawable(loadingBarPatch);
//        loadingBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
//        progress = base.getHealth()/base.getMaxHealth();
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        // This would be getHealth()/getMaxHealth()
//
//        loadingBarBackground.draw(batch, getX(), getY(), getWidth() * getScaleX(), getHeight() * getScaleY());
//        loadingBar.draw(batch, getX(), getY(), progress * getWidth() * getScaleX(), getHeight() * getScaleY());
//    }

