package manager;

import bullets.Bullet;
import display.Display;
import enemies.Enemy;
import entity.Player;
import setUp.gameSetUp;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class gameManager implements KeyListener {

    public static ArrayList<Bullet> bullet;
    private Player player;
    private ArrayList<Enemy> enemies;

    private long current;
    private long delay;
    private int health;
    private int score;
    private boolean start;

    public gameManager() {

    }

    public void init() {
        Display.frame.addKeyListener(this);
        player = new Player((gameSetUp.gameWidth / 2) + 50, (gameSetUp.gameHeight - 60) + 50);
        player.init();
        bullet = new ArrayList<>();
        enemies = new ArrayList<>();
        current = System.nanoTime();
        delay = 2000;

        health = player.getHealth();
        score = 0;
    }

    public void tick() {
        if (start) {
            player.tick();
            for (Bullet value : bullet) {
                value.tick();
            }
            long breaks = (System.nanoTime() - current) / 100000;
            if (breaks > delay) {
                for (int i = 0; i < 2; i++) {
                    Random rand = new Random();
                    int randX = rand.nextInt(450);
                    int randY = rand.nextInt(450);
                    if (health > 0) {
                        enemies.add(new Enemy(randX, -randY));
                    }
                }
                current = System.nanoTime();
            }
            //enemies
            for (Enemy enemy : enemies) {
                enemy.tick();
            }
        }
    }

    public void render(Graphics g) {
        if (start) {
            player.render(g);
            // bullet
            for (Bullet value : bullet) {
                value.render(g);
            }
            for (int i = 0; i < bullet.size(); i++) {
                if (bullet.get(i).getY() <= 50) {
                    bullet.remove(i);
                    i--;
                }
            }
            //enemies
            for (Enemy enemy : enemies) {
                if (!(enemy.getX() <= 50
                        || enemy.getX() >= 450 - 50
                        || enemy.getY() >= 450 - 50)) {
                    if (enemy.getY() >= 50) {
                        enemy.render(g);
                    }
                }
            }
            //enemies & player collision
            for (int i = 0; i < enemies.size(); i++) {
                int ex = enemies.get(i).getX();
                int ey = enemies.get(i).getY();

                int px = player.getX();
                int py = player.getY();

                if (px < ex + 50 && px + 60 > ex &&
                        py < ey + 50 && py + 60 > ey) {
                    enemies.remove(i);
                    i--;
                    health--;
                    System.out.println(health);
                    if (health <= 0) {
                        enemies.removeAll(enemies);
                        player.setHealth(0);
                        start = false;
                    }
                }
                // bullets  && enemy collision
                for (int j = 0; j < bullet.size(); j++) {
                    int bx = bullet.get(j).getX();
                    int by = bullet.get(j).getY();

                    if (ex < bx + 6 && ex + 50 > bx
                            && ey < by + 6 && ey + 50 > by) {
                        enemies.remove(i);
                        i--;
                        bullet.remove(j);
                        j--;
                        score = score + 5;
                    }
                }
                g.setColor(Color.blue);
                g.setFont(new Font("arial", Font.BOLD, 40));
                g.drawString("Score : " + score, 70, 500);
            }
        } else {
            g.setFont(new Font("arial", Font.PLAIN, 33));
            g.drawString("Press enter to start", 100, (gameSetUp.gameHeight / 2) + 50);
        }
    }

    public void keyPressed(KeyEvent e) {
        int source = e.getKeyCode();
        if (source == KeyEvent.VK_ENTER) {
            start = true;
            init();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
    }
}
