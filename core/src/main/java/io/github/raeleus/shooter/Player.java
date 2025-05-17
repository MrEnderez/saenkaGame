package io.github.raeleus.shooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Player {
    private Texture playerLeft;
    private Texture playerRight;
    private Sprite sprite;
    private Rectangle bounds;
    private Vector2 position;
    private float speed = 400f;
    private int health = 200;
    private boolean isAlive = true;

    private float velocityY = 0f;
    private float gravity = -20f;
    private float jumpVelocity = 500f;
    private boolean onGround = true;
    private float groundY = 150f; // Zemin yüksekliği

    private ArrayList<Bullet> bullets = new ArrayList<>();
    private float fireCooldown = 0.3f;
    private float timeSinceLastShot = 0f;

    private boolean facingRight = false;

    public Player(float x, float y) {
        playerLeft = new Texture("player_Left.png");
        playerRight = new Texture("player_Right.png");
        sprite = new Sprite(playerRight);
        sprite.setSize(sprite.getWidth() * 2f, sprite.getHeight() * 2f);
        bounds = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        position = new Vector2(x, y);
        facingRight = true;
    }

    public void update(float deltaTime) {
        timeSinceLastShot += deltaTime;

        // Yatay hareket
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A))
        {
            position.x -= speed * deltaTime;
            facingRight = false;
            sprite.setTexture(playerLeft);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D))
        {
            position.x += speed * deltaTime;
            facingRight = true;
            sprite.setTexture(playerRight);
        }

        // Zıplama
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W) && onGround) {
            velocityY = jumpVelocity;
            onGround = false;
        }

        // Yerçekimi
        velocityY += gravity;
        position.y += velocityY * deltaTime;

        if (position.y <= groundY) {
            position.y = groundY;
            velocityY = 0;
            onGround = true;
        }

        sprite.setPosition(position.x, position.y);
        bounds.setPosition(position.x, position.y);

        // Ekran dışına çıkma sınırı
        position.x = Math.max(0, Math.min(position.x, Gdx.graphics.getWidth() - sprite.getWidth()));

        // Mouse sol tık ile ateş
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && timeSinceLastShot >= fireCooldown) {
            bullets.add(new Bullet(position.x + sprite.getWidth() / 2f, position.y + sprite.getHeight() / 2f, facingRight));
            timeSinceLastShot = 0f;
        }

        // Mermileri güncelle
        for (Bullet bullet : bullets) {
            bullet.update(deltaTime);
        }

        bullets.removeIf(bullet -> !bullet.isActive() || bullet.isOffScreen());
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }


    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getCenterPosition() {
        return new Vector2(position.x + sprite.getWidth() / 2f, position.y + sprite.getHeight() / 2f);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Bullet shoot() {
        float bulletX = facingRight ? position.x + sprite.getWidth() : position.x - 16;
        float bulletY = position.y + sprite.getHeight() / 2f;
        return new Bullet(bulletX, bulletY, facingRight); // facingRight yön bilgisini kullanıyor
    }

    public void dispose() {
        playerLeft.dispose();
        playerRight.dispose();
        for (Bullet bullet : bullets) {
            bullet.dispose();
        }
    }
}
