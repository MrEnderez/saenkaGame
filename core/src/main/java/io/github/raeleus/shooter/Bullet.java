package io.github.raeleus.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    private static final float SPEED = 500f;
    private Texture texture;
    private Vector2 position;
    private boolean movingRight;
    private Rectangle bounds;
    private boolean active = true;

    public Bullet(float x, float y, boolean movingRight) {
        texture = new Texture("playerBullet.png");
        position = new Vector2(x, y);
        this.movingRight = movingRight;
        bounds = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public void update(float deltaTime) {
        position.x += (movingRight ? 1 : -1) * SPEED * deltaTime;
        bounds.setPosition(position.x, position.y);
    }

    public void render(SpriteBatch batch) {
        if (active)
        {
            batch.draw(texture, position.x, position.y);
        }
    }

    public boolean isOffScreen() {
        return position.x < 0 || position.x > com.badlogic.gdx.Gdx.graphics.getWidth();
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public void dispose() {
        texture.dispose();
    }
}
