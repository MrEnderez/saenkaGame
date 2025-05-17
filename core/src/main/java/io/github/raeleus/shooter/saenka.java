package io.github.raeleus.shooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class saenka extends Game {
    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch(); // SpriteBatch'i başlat

        // İlk ekran olarak FirstScreen'i ayarla
        this.setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render(); // Ekranların render edilmesini sağlar
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height); // Ekran boyutunu ayarlar
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        batch.dispose(); // Kaynakları temizle
    }




}
