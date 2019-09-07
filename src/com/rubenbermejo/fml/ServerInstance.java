package com.rubenbermejo.fml;

import java.io.*;

public class ServerInstance extends Thread {

    private int serverValue;
    private OutputStream os;
    private Process serverProcess;

    ServerInstance(int server) {
        serverValue = server;
    }

    @Override
    public void run() {
        File server = FileManager.getServer(serverValue);

        try {
            serverProcess = Runtime.getRuntime().exec("java -jar \"" + server.getAbsolutePath() + "\" nogui");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            InputStream is = serverProcess.getInputStream();
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

    void sendCommand(String command) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(command);
        bw.close();
    }

    void stopServer() throws IOException {
        sendCommand("stop");
        try {
            int exitVal = serverProcess.waitFor();
            System.out.println("Server process has terminated with exit code " + exitVal);
        } catch (InterruptedException irre) {
            irre.printStackTrace();
        }
    }
}
