package com.abderrahimlach;

import com.abderrahimlach.connection.PlayerConnection;

import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MinecraftServer {

    private static MinecraftServer instance;

    private ServerSocket serverSocket;
    private int port = 25565;

    public MinecraftServer() throws Exception {
        instance = this;
        this.serverSocket = new ServerSocket(port);

        warn("Socket is now accepting traffics.");

        new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        final Socket socket = serverSocket.accept();
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    new PlayerConnection(socket);
                                } catch (IOException ex) {
                                    if (ex instanceof EOFException) {
                                        warn("Connection terminated");
                                    } else if (ex instanceof SocketException) {
                                        warn("Socket closed");
                                    } else {
                                        ex.printStackTrace();
                                    }
                                }
                            }
                        }.start();
                    } catch (IOException ex) {
                        return;
                    }
                }
            }
        }.start();
    }



    public ServerSocket getSocket() {
        return serverSocket;
    }

    public static MinecraftServer getInstance(){
        return instance;
    }

    public int getPort() {
        return port;
    }
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void warn(String message){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        System.out.println("["+df.format(new Date(System.currentTimeMillis()))+"] Warn: "+message);
    }

}
