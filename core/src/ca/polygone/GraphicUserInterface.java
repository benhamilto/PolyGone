package ca.polygone;
import ca.polygone.ca.polygone.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class GraphicUserInterface extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    public GraphicUserInterface(){
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MenuScreen(this));
    }

    public void render(){
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}

