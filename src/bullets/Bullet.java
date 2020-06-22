package bullets;

import java.awt.*;

public class Bullet {

    private final int x;
    private final int speed;
    private int y;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 10;
    }

    public void tick() {
        y -= speed;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, 6, 10);
        g.setColor(Color.BLACK);
    }
}
