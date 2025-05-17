package io.github.raeleus.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet {
    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float speed = 300f;
    private Rectangle bounds;
    private int damage = 25; // Mermi hasar değeri

    public EnemyBullet(Vector2 startPos, Vector2 targetPos) {
        this.texture = new Texture("enemyBullet.png");
        this.position = new Vector2(startPos);
        this.velocity = new Vector2(targetPos).sub(startPos).nor().scl(speed);
    }

    public void update(float deltaTime) {
        position.mulAdd(velocity, deltaTime);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public boolean isOffScreen() {
        return position.x < 0 || position.x > 1920 || position.y < 0 || position.y > 1080;
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void dispose() {
        texture.dispose();
    }
}
