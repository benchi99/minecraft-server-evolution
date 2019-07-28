package com.rubenbermejo.fml;

import java.util.Scanner;

public class ThreadManager {

    private static int currentServer = FileManager.currentServer;
    static ServerInstance instance;
    private static boolean on = true;

    public static void start() {

        while (on) {

        }


        loadAndStart(0);


    }

    static void loadAndStart(int serverFile) {
        instance = new ServerInstance(currentServer);

        instance.start();
    }

    static String readCommand() {
        return new Scanner(System.in).next();
    }



}
