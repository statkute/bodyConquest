package com.cauldron.bodyconquest.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cauldron.bodyconquest.rendering.BodyConquest;
import com.cauldron.bodyconquest.tests.DragAndDropTest;
import com.cauldron.bodyconquest.tests.GdxTest;
import com.cauldron.bodyconquest.Animator;
// import com.cauldron.bodyconquest.Game;

public class DesktopLauncher {
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    config.title = "Body Wars";

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    final int SCREEN_HEIGHT = (int) screenSize.getHeight();
    final int SCREEN_WIDTH = (int) screenSize.getWidth();

    boolean fullscreen = false;
    if (fullscreen) {
      config.height = SCREEN_HEIGHT;
      config.width = SCREEN_WIDTH;
      config.fullscreen = true;
    } else {
      config.height = SCREEN_HEIGHT / 2;
      config.width = SCREEN_WIDTH / 2;
      config.fullscreen = false;
      config.x = SCREEN_WIDTH - config.width;
    }

    // config.foregroundFPS = 120;

    config.vSyncEnabled = false; // Setting to false disables vertical sync
    config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
    config.backgroundFPS = 0; // Setting to 0 disables background fps throttling

    new LwjglApplication(new BodyConquest(), config);
    // new LwjglApplication(new DragAndDropTest(), config);
  }
}
