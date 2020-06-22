package Main;

import setUp.gameSetUp;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws SocketException, UnknownHostException {
        gameSetUp game = new gameSetUp("Network Programming", 500, 600);
        game.start();
    }
}