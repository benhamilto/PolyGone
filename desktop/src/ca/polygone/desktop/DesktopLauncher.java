package ca.polygone.desktop;

import ca.polygone.Environment;
import ca.polygone.GraphicUserInterface;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {



	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.addIcon("desktop/assets/PolyGoneIcon32.png", Files.FileType.Internal);
		config.height = 563;
		config.width = 1000;
		new LwjglApplication(new GraphicUserInterface(), config);

	}
}
