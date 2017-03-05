package ca.polygone.ca.polygone.screens;

import ca.polygone.GraphicUserInterface;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by bhamilto on 3/4/17.
 */
public abstract class PolyGoneScreen implements Screen{
    private GraphicUserInterface game;

    public PolyGoneScreen(GraphicUserInterface game) {
        this.game = game;
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void show () {
    }

    @Override
    public void hide () {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
    }

}
