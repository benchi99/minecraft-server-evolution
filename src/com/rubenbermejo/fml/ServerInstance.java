package com.rubenbermejo.fml;

import java.io.*;

public class ServerInstance extends Thread {

    static File server;
    static int serverValue;
    static InputStream is;
    static OutputStream os;
    static BufferedWriter bw;
    static OutputStreamWriter osw;
    static Process serverProcess;

    boolean end = false;

    public ServerInstance(int server) {
        serverValue = server;
    }

    @Override
    public void run() {
        server = FileManager.getServer(serverValue);

        try {
            //serverProcess = new ProcessBuilder("cmd.exe -c java -jar \"" + server.getAbsolutePath() + "\" nogui").start();
            serverProcess = Runtime.getRuntime().exec("java -jar \"" + server.getAbsolutePath() + "\" nogui");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            end = true;
        }

        try {
            is = serverProcess.getInputStream();
            os = serverProcess.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            System.out.println("Starting server version" + server.getName());
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void sendCommand(String command) throws IOException {
        osw = new OutputStreamWriter(os);
        bw = new BufferedWriter(osw);
        bw.write(command);
        bw.close();
    }

    public static void stopServer() throws IOException {
        osw = new OutputStreamWriter(os);
        bw = new BufferedWriter(osw);
        bw.write("stop");
        bw.close();
        try {
            int exitVal = serverProcess.waitFor();
            System.out.println("Server process has terminated with exit code " + exitVal);
        } catch (InterruptedException irre) {
            irre.printStackTrace();
        }
    }


}
