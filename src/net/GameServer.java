package net;

import setUp.gameSetUp;

import java.io.*;
import java.net.*;

public class GameServer extends Thread {
    private DatagramSocket socket;
    private gameSetUp game;

    public GameServer(gameSetUp game) throws SocketException {
        this.game = game;
        this.socket = new DatagramSocket(9999);
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());
            System.out.println("CLIENT[" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]: " + message);
            if (message.trim().equalsIgnoreCase("ping")) {
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}