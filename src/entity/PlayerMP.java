package entity;

import java.net.InetAddress;

public class PlayerMP extends Player {

    public InetAddress ipAddress;
    public int port;

    public PlayerMP(int x, int y) {
        super(x, y);
    }
}
