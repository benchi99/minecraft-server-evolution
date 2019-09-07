package com.rubenbermejo.fml;

import java.io.IOException;
import java.util.Scanner;

public class ThreadManager {

    private static int currentServer = FileManager.currentServer;
    private static ServerInstance serverInstance;
    private static boolean on = true;

    public static void start() {

        loadAndStart(currentServer);

        while (on) {
            switch (readCommand()) {
                case "send":
                    System.out.print("Type in the command you want to send to the server: ");
                    sendCommand(readCommand());
                    break;
                case "skip":
                    skipToNextServer();
                case "stop":
                    stopServer();
                    break;
                case "exit":
                    System.out.println("Terminating the server...");
                    stopServer();
                    System.out.println("Exiting...");
                    stopThreadManager();
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    private static void skipToNextServer() {

    }

    private static void sendCommand(String command) {
        try {
            serverInstance.sendCommand(command);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    static void stopServer() {
        try {
            serverInstance.stopServer();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    static void loadAndStart(int serverFile) {
        serverInstance = new ServerInstance(serverFile);

        serverInstance.start();
    }

    public static String readCommand() {
        return new Scanner(System.in).nextLine().toLowerCase();
    }

    public static void stopThreadManager() {    on = false;    }



}
