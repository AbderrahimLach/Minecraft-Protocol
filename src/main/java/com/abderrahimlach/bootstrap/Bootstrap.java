package com.abderrahimlach.bootstrap;

import com.abderrahimlach.MinecraftServer;

import java.util.Scanner;

public class Bootstrap {



    public static void main(String[] args) throws Exception {
        MinecraftServer server = new MinecraftServer();

        boolean run = true;

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext() && run) {
            String input = scanner.next();

            if (input.equalsIgnoreCase("exit")) {
                run = false;
                server.warn("Stopping");
                server.stop();
                server.warn("Closing now");
                System.exit(0);
            }

        }
    }

}
