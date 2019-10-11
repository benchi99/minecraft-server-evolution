package com.rubenbermejo.fml;

public class Main {

    public static void main(String[] args) {
        // Load all server files found in the servers folder.
        boolean status = FileManager.loadServerFiles();

        if (!status)
            // If an error loading the servers happened, exit application.
            System.exit(1);
        else {
            // If everything went to plan, start the whole thing.
            System.out.println("All files loaded correctly!");
            ThreadManager.start();
        }
    }

}
