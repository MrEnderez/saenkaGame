package io.github.raeleus.shooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class WaveSpawner {
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Texture[] maps;
    private Sprite background;
    private float spawnInterval = 5f;
    private float timeSinceLastSpawn = 0f;

    private int currentWave = 1;
    private int enemiesSpawnedInWave = 0;
    private int maxEnemiesPerWave = 5;

    private Random random = new Random();
    private float screenWidth = 1920;

    public WaveSpawner(){
        maps = new Texture[] {
            new Texture("arkaplancol.png"),
            new Texture("kale.png"),
            new Texture("Buz.png"),
            new Texture("cehennem.png"),
            new Texture("orman.png")
        };
    }

    public void update(float deltaTime, Vector2 playerPosition, Rectangle playerBounds, Player player) {
        timeSinceLastSpawn += deltaTime;

        background = new Sprite(maps[currentWave - 1]);

        // Düşman doğurma mantığı
        if (enemiesSpawnedInWave < maxEnemiesPerWave && timeSinceLastSpawn >= spawnInterval) {
            spawnEnemy();
            enemiesSpawnedInWave++;
            timeSinceLastSpawn = 0f;
        }

        for (Enemy enemy : enemies)
        {
            if (!enemy.isAlive()) continue;
            enemy.update(deltaTime, playerPosition);

            for (Bullet bullet : player.getBullets()) {
                Rectangle bulletBounds = bullet.getBounds();
                Rectangle enemyBounds = new Rectangle(enemy.getPosition().x, enemy.getPosition().y, enemy.sprite.getWidth(), enemy.sprite.getHeight()); // Texture boyutunu kullan

                if (bulletBounds.overlaps(enemyBounds)) {
                    enemy.takeDamage(50f); // Örnek hasar
                    bullet.deactivate(); // Bu mermiyi yok et (aşağıda açıklanacak)
                    break; // birden fazla vurmasın
                }
            }
        }

        enemies.removeIf(e -> !e.isAlive());

        // Düşmanları ve mermilerini güncelle
        Iterator<Enemy> iter = enemies.iterator();
        while (iter.hasNext()) {
            Enemy enemy = iter.next();
            enemy.update(deltaTime, playerPosition);

            // Mermi çarpışma kontrolü
            for (EnemyBullet bullet : enemy.getBullets()) {
                if (playerBounds.contains(bullet.getPosition())) {
                    player.takeDamage(25); // Mermi başına 25 hasar
                    bullet.dispose(); // çarpan mermiyi yok et
                    enemy.getBullets().remove(bullet);
                    break;
                }
            }

            // Ölen düşmanı listeden çıkar
            if (!enemy.isAlive()) {
                iter.remove();
            }
        }

        // Dalga bitmişse yenisini başlat
        if (enemiesSpawnedInWave >= maxEnemiesPerWave && enemies.isEmpty()) {
            startNextWave();
        }
    }

    public void render(SpriteBatch batch) {
        background.draw(batch);
        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }
    }

    private void spawnEnemy() {
        float randomX = random.nextFloat() * (screenWidth - 64);
        float spawnY = 170f;
        enemies.add(new Enemy(randomX, spawnY));
    }

    private void startNextWave() {
        currentWave++;
        maxEnemiesPerWave += 2;
        spawnInterval = Math.max(1f, spawnInterval - 0.5f);
        enemiesSpawnedInWave = 0;
    }

    public void dispose() {
        for (Enemy enemy : enemies) {
            enemy.dispose();
        }
    }
}
