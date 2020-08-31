package de.yugimuo.websocket;


import de.yugimuo.utilities.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static de.yugimuo.utilities.Logger.LOG;

public class Server {
    private final List<StandardDataRetrieveThread> retrieveThreadList = new ArrayList<>();
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new Thread(() -> {
            while (true) {
                Socket socket = null;
                try {
                    while (( socket = serverSocket.accept() ) != null) {
                        LOG("Registering socket " + socket.getInetAddress(), Logger.LoggerType.INFO);
                        retrieveThreadList.add(new StandardDataRetrieveThread(socket));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}