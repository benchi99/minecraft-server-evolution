package com.rubenbermejo.fml;

import java.io.IOException;
import java.util.Scanner;

class ThreadManager {

    private static int currentServer = 0;
    private static ServerInstance serverInstance;
    private static boolean on = true;

    static void start() {
        loadAndStart(currentServer);

        while (on) {
            switch (readCommand()) {
                case "send":
                    System.out.print("Type in the command you want to send to the server: ");
                    sendCommand(readCommand());
                    break;
                case "skip":
                    skipToNextServer();
                    break;
                case "stop":
                    stopServer();
                    break;
                case "exit":
                    System.out.println("Terminating the server...");
                    stopServer();
                    System.out.println("Exiting...");
                    stopThreadManager();
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        }
    }

    private static void skipToNextServer() {
        currentServer++;
        stopServer();
        loadAndStart(currentServer);
    }

    private static void sendCommand(String command) {
        try {
            serverInstance.sendCommand(command);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void stopServer() {
        try {
            serverInstance.stopServer();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private static void loadAndStart(int serverFile) {
        serverInstance = new ServerInstance(serverFile);

        serverInstance.start();
    }

    private static String readCommand() {
        return new Scanner(System.in).nextLine().toLowerCase();
    }

    private static void stopThreadManager() {    on = false;    }

}
