package com.rubenbermejo.fml;

public class Main {

    public static void main(String[] args) {
        boolean status = FileManager.loadServerFiles();

        if (!status)
            System.exit(1);
        else {
            System.out.println("All files loaded correctly!");
            ThreadManager.start();
        }
    }

}
