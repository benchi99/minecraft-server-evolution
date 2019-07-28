package com.rubenbermejo.fml;

import java.io.*;

public class ServerInstance extends Thread {

    static File server;
    static int serverValue;
    static InputStream is;
    static OutputStream os;
    static BufferedWriter bw;

    boolean end = false;

    public ServerInstance(int server) {
        serverValue = server;
    }

    @Override
    public void run() {
        server = FileManager.getServer(serverValue);

        try {
            Process serverProcess = new ProcessBuilder(server.getAbsolutePath()).start();

            is = serverProcess.getInputStream();
            os = serverProcess.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedReader br = new BufferedReader(isr);
            bw = new BufferedWriter(osw);

            System.out.println("Starting server version" + server.getName());
            while (!end) {
                String line = br.readLine();
                System.out.println(line);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void sendCommand(String command) throws IOException {
        bw.write(command);
    }

    public static void stopServer() throws IOException {
        bw.write("stop");
    }


}
