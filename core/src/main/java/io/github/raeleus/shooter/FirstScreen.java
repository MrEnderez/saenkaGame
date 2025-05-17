package io.github.raeleus.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class FirstScreen implements Screen {
    private saenka game; // Ana oyun referansı
    private WaveSpawner waveSpawner;
    private Player player;
    private SpriteBatch batch;

    public FirstScreen(saenka game) {
        this.game = game;
    }

    @Override
    public void show() {
        float groundY = 150f;
        player = new Player(250, groundY);
        waveSpawner = new WaveSpawner();
        batch = new SpriteBatch();

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        // ESC tuşuna basılırsa ana menüye dön
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
            return;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.isAlive()) {
            player.update(delta);
            waveSpawner.update(delta, player.getPosition(), player.getBounds(), player);
        } else {
            // Oyuncu öldü, ana menüye dön
            game.setScreen(new MainMenu(game));
            return;
        }

        batch.begin();
        waveSpawner.render(batch);
        player.render(batch);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        waveSpawner.dispose();
        player.dispose();
    }
}
