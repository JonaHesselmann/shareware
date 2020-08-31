package de.yugimuo.websocket;


import de.yugimuo.utilities.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static de.yugimuo.utilities.Logger.LOG;


public class Client {
    Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {

        this.clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        new Thread(() -> {
            String response = "";
            try {
                while (( response = in.readLine() ) != null) {

                    LOG("Recieved back message: " + response, Logger.LoggerType.INFO);
                }
            } catch (Exception ignored) {
            }
        }).start();
        return "resp";
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
