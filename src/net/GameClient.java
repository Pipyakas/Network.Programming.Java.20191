package net;

import manager.gameManager;
import setUp.gameSetUp;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread {
    private InetAddress ipAddress;
    private DatagramSocket socket;
    private gameSetUp game;

    public GameClient (gameSetUp game, String ipAddress) throws SocketException, UnknownHostException {
        this.game = game;
        this.socket = new DatagramSocket();
        this.ipAddress = InetAddress.getByName(ipAddress);
    }

    public void run(){
        while (true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = new String(packet.getData());
            System.out.println("SERVER: " + message);
        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 9999);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
