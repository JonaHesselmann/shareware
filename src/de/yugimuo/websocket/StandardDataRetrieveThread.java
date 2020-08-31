package de.yugimuo.websocket;

import com.sun.istack.internal.NotNull;
import de.yugimuo.utilities.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static de.yugimuo.utilities.Logger.LOG;

/**
 * @author Wazed
 * Created on 31.08.2020
 */
public class StandardDataRetrieveThread {

    private final PrintWriter out;
    private final BufferedReader in;

    /**
     * @param socket The given socket, that got accepted by the ServerSocket
     * @throws IOException Either thrown when the connection gets interrupted or when the connection closes completely
     * @see de.yugimuo.websocket.Server
     */
    public StandardDataRetrieveThread(@NotNull Socket socket) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(() -> {
            while (true) {
                try {
                    String response = "";
                    while (( response = in.readLine() ) != null) {

                        if (response.equals("hello server")) {
                            out.println("hello client");
                        } else {
                            LOG("Recieved Invalid Handshake | Message: " + response, Logger.LoggerType.ERROR);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
