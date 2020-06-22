package display;

import javax.swing.*;
import java.awt.*;

public class Display {

    public static JFrame frame;
    private final String title;
    private final int width;
    private final int height;
    private Canvas canvas;


    public Display(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        createDisplay();
    }

    public void createDisplay() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setBackground(new Color(212, 154, 140));
        canvas.setFocusable(false);
        frame.add(canvas);
        frame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
