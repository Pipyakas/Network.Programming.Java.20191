package entity;

import bullets.Bullet;
import display.Display;
import graphics.loadImage;
import manager.gameManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
    private int y;
    private int x;
    private boolean left, right;
    private boolean fire;

    private long current;
    private long delay;
    private int health;
    private String username;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void init() {
        Display.frame.addKeyListener(this);
        current = System.nanoTime();
        delay = 100;
        health = 3;
    }

    public void tick() {
        if (!(health <= 0)) {
            if (left) {
                if (x >= 60) {
                    x -= 5;
                }
            }
            if (right) {
                if (x <= 450 - 70) {
                    x += 5;
                }
            }
            if (fire) {
                long breaks = (System.nanoTime() - current) / 100000;
                if (breaks > delay) {
                    gameManager.bullet.add(new Bullet(x + 25, y));
                }
                current = System.nanoTime();
            }
        }
    }

    public void render(Graphics g) {
        if (!(health <= 0)) {
            g.drawImage(loadImage.player,
                    x, y, 60, 60, null);
        }
    }

    public void keyPressed(KeyEvent e) {
        int source = e.getKeyCode();
        if (source == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (source == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (source == KeyEvent.VK_SPACE) {
            fire = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int source = e.getKeyCode();
        if (source == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (source == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (source == KeyEvent.VK_SPACE) {
            fire = false;
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
