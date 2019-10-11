package com.rubenbermejo.fml;

import java.io.*;

/**
 * Class that handles a Server Instance
 */
public class ServerInstance extends Thread {

    private int serverValue;
    private OutputStream os;
    private Process serverProcess;

    /**
     * ServerInstance needs a server value so it knows which one to start.
     * @param server Server value.
     */
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

    /**
     * Sends a command to the attached server process.
     *
     * @param command Command to send.
     * @throws IOException I/O Error.
     */
    void sendCommand(String command) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write(command);
        bw.close();
    }

    /**
     * Stops the server and waits for its exit.
     *
     * @throws IOException I/O Error.
     */
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
