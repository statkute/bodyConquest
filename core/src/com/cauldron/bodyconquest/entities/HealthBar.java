package com.cauldron.bodyconquest.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;

public class HealthBar extends ProgressBar {


  public HealthBar(int width, int height){


    super(0f,1f,0.01f,false,new ProgressBarStyle());

    getStyle().background = HealthBarHelper.getColoredDrawable(width, height, Color.RED);
    getStyle().knob = HealthBarHelper.getColoredDrawable(0, height, Color.GREEN);
    getStyle().knobBefore = HealthBarHelper.getColoredDrawable(width, height, Color.GREEN);

    setWidth(width);
    setHeight(height);

    setAnimateDuration(0.0f);
    setValue(1f);

    setAnimateDuration(0.25f);

  }






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

