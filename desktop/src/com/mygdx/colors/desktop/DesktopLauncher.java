package com.mygdx.colors.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.colors.ColorsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = ColorsGame.TITLE;
		config.width = 1024;
		config.height = 600;
		config.resizable=false;
		config.useGL30 = false;
		new LwjglApplication(new ColorsGame(), config);
	}
}
