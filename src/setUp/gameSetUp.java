package setUp;

import display.Display;
import graphics.loadImage;
import manager.gameManager;
import net.GameClient;
import net.GameServer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.net.SocketException;
import java.net.UnknownHostException;

public class gameSetUp extends Canvas implements Runnable {

    public static final int gameWidth = 400;
    public static final int gameHeight = 400;

    private final String title;
    private final int width;
    private final int height;

    private Thread thread;
    private boolean running;

    private BufferStrategy buffer;
    private Graphics g;
    private int y;
    private boolean start;

    private gameManager manager;
    private Display display;

    private GameClient socketClient;
    private GameServer socketServer;

    public gameSetUp(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public void init() {
        display = new Display(title, width, height);
        loadImage.init();
        manager = new gameManager();
        manager.init();
        start = true;
    }

    public synchronized void start() throws SocketException, UnknownHostException {
        if (running)
            return;
        running = true;
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        if (JOptionPane.showConfirmDialog(this, "Run a server?") ==0)        {
            socketServer = new GameServer(this);
            socketServer.start();
        }
        socketClient = new GameClient(this, "localhost");
        socketClient.start();

        socketClient.sendData("ping".getBytes());
    }

    public synchronized void stop() {
        if (!(running))
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void tick() {
        manager.tick();
    }

    public void render() {
        buffer = display.getCanvas().getBufferStrategy();
        if (buffer == null) {
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = buffer.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        //draw

        g.drawImage(loadImage.image, 50, 50, gameWidth, gameHeight, null);

        manager.render(g);
        // menu

        //end of draw
        buffer.show();
        g.dispose();
    }


    public void run() {
        init();

        int fps = 60;
        double timePerTick = 1000000000.0 / fps;
        double delta = 0;
        long current = System.nanoTime();

        while (running) {
            delta = delta + (System.nanoTime() - current) / timePerTick;
            current = System.nanoTime();
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
    }
}
