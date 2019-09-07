package com.rubenbermejo.fml;

import java.io.File;

class FileManager {

    private static File[] serverFiles;

    /**
     * Loads all files located in the serverfiles/ directory.
     *
     * It makes sure that all files loaded in there are .jar files.
     * TODO: At some point in the code, I should try to make sure that the files are minecraft_server files.
     *
     * @return Status of the action.
     */
    static boolean loadServerFiles() {
        boolean status = true;
        serverFiles = new File("serverfiles").listFiles();

        if (serverFiles == null || serverFiles.length == 0) {
            System.err.println("[FileManager] FATAL ERROR: No server files found. Exiting...");
            status = false;
        } else {
            for (File file : serverFiles) {
                System.out.print("[FileManager] Checking " + file.getName() + ".");
                if (!file.getName().endsWith(".jar")) {
                    System.out.println("[FileManager] FATAL ERROR: File " + file.getName() + "is NOT a Java Executable file. Please remove it from the serverfiles directory and restart the application.");
                    status = false;
                    break;
                }
                System.out.print(". OK\n");
            }
        }
        return status;
    }

    static File getServer(int server) {
        return serverFiles[server];
    }

}
