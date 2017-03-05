package ca.polygone.desktop;

import ca.polygone.GraphicUserInterface;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.addIcon("desktop/assets/PolyGoneIcon32.png", Files.FileType.Internal);

		new LwjglApplication(new GraphicUserInterface(), config);
	}
}
