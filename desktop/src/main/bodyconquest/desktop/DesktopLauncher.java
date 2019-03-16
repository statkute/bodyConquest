package main.bodyconquest.desktop;

import java.awt.Dimension;
import java.awt.Toolkit;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import main.com.bodyconquest.rendering.BodyConquest;

public class  DesktopLauncher {
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

    config.title = "Body Conquest";

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

    config.vSyncEnabled = true; // Setting to false disables vertical sync
    config.foregroundFPS = 0; // Setting to 0 disables foreground fps throttling
    config.backgroundFPS = 0; // Setting to 0 disables background fps throttling

    new LwjglApplication(new BodyConquest(), config);
  }
}
