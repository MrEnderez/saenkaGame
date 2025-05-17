package io.github.raeleus.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;

public class CreditsScreen implements Screen {

    private Game game;
    private Texture creditsImage;
    private SpriteBatch batch;

    public CreditsScreen(Game game) {
        this.game = game;
        this.creditsImage = new Texture(Gdx.files.internal("credits.png")); // resmi assets içine koy
        this.batch = new SpriteBatch();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // ESC kontrolü
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenu((saenka) game)); // ESC'ye basıldığında ana menüye dön
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(creditsImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        creditsImage.dispose();
        batch.dispose();
    }
}
