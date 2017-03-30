package ca.polygone;
import ca.polygone.ca.polygone.screens.GameOverScreen;
import ca.polygone.ca.polygone.screens.MenuScreen;
import ca.polygone.ca.polygone.screens.VictoryScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GraphicUserInterface extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    Sound gameMusic;
    public Environment environment;

    public GraphicUserInterface(){
        Sound gameMusic = Gdx.audio.newSound(Gdx.files.internal("core/assets/menusong.wav"));
        gameMusic.play();
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MenuScreen(this));
    }


    public void render(){
        super.render();
        if(environment != null) {
            if (environment.isPlayerTurnDone()) {
                environment.nonPlayerTurn();
                environment.resetPlayerPieces();
            }
            if( environment.checkForDefeat()) {
                environment = null;
                this.getScreen().dispose();
                this.setScreen(new GameOverScreen(this));
            }
            else if(environment.checkForVictory()){
                environment = null;
                this.getScreen().dispose();
                this.setScreen(new VictoryScreen(this));
            }

        }

    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}

