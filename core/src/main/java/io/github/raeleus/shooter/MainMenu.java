package io.github.raeleus.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MainMenu implements Screen {
    private Texture background;
    private SpriteBatch batch;
    private saenka game;
    private Stage stage;
    private Skin skin;

    public MainMenu(saenka game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // inputları sahneye yönlendir
        this.background = new Texture(Gdx.files.internal("mainmenu.png"));
        this.batch = new SpriteBatch();


        skin = new Skin(Gdx.files.internal("uiskin.json")); // LibGDX'in default UI skin'i

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Butonlar
        TextButton playButton = new TextButton("Play", skin);
        TextButton settingsButton = new TextButton("Credits", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        // Olaylar
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new FirstScreen(game)); // GameScreen senin oyun ekranın olmalı
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CreditsScreen(game)); // Credits ekranına geç
            }
        });


        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Oyunu kapat
            }
        });

        // UI yerleşimi
        table.add(playButton).pad(10).row();
        table.add(settingsButton).pad(10).row();
        table.add(exitButton).pad(10).row();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Arka planı çiz
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // UI sahnesini çiz
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
