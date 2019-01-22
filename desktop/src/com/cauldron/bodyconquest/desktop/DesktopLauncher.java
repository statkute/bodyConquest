package com.cauldron.bodyconquest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cauldron.bodyconquest.Animator;
import com.cauldron.bodyconquest.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
        config.title = "Body Wars";
		new LwjglApplication(new Animator(), config);
	}
}
