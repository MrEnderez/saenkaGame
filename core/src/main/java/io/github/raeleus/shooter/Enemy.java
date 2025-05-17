package io.github.raeleus.shooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

public class Enemy {
    public Texture texture;
    public Sprite sprite;
    public Vector2 position;
    public float speed;
    public float health = 100f;
    public boolean isAlive = true;

    private ArrayList<EnemyBullet> bullets = new ArrayList<>();
    private float fireCooldown = 1.5f;
    private float timeSinceLastShot = 0f;
    private float fireRange = 400f;

    public Enemy(float x, float y) {
        texture = new Texture("enemy.png");
        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth() * 2, sprite.getHeight() * 2);
        position = new Vector2(x, y);
        speed = 100f;
    }

    public void update(float deltaTime, Vector2 playerPosition) {
        if (!isAlive) return;

        timeSinceLastShot += deltaTime;

        float distanceToPlayer = position.dst(playerPosition);

        if (distanceToPlayer < fireRange) {
            if (timeSinceLastShot >= fireCooldown) {
                bullets.add(new EnemyBullet(new Vector2(sprite.getX() + sprite.getWidth() / 2f, sprite.getY() + sprite.getHeight() / 2f), playerPosition.cpy()));
                timeSinceLastShot = 0f;
            }
            // Ateş mesafesindeyse yaklaşma, sadece ateş et
        } else {
            // Yakın değilse oyuncuya yönel
            Vector2 direction = new Vector2(playerPosition).sub(position).nor();
            position.add(direction.scl(speed * deltaTime));
        }

        sprite.setPosition(position.x, position.y);

        // Mermileri güncelle
        bullets.removeIf(EnemyBullet::isOffScreen);
        for (EnemyBullet bullet : bullets) {
            bullet.update(deltaTime);
        }
    }

    public void render(SpriteBatch batch) {
        if (!isAlive) return;

        sprite.draw(batch);
        for (EnemyBullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public ArrayList<EnemyBullet> getBullets() {
        return bullets;
    }

    public void dispose() {
        texture.dispose();
        for (EnemyBullet bullet : bullets) {
            bullet.dispose();
        }
    }
}
