package controller;

import java.io.*;
import java.net.*;

public class ConnectToServer {

    private static Socket out;

    public static void connect()throws IOException {
        out = new Socket(Config.ip_address,8888);
    }

    public static void sendMessageToServer(String message) throws IOException {

        /* Write to server using printWriter */
        System.out.println(message);
        PrintWriter output = new PrintWriter(out.getOutputStream(),true);
        output.println(message);
    }


    public static String getMessageFromServer() throws IOException{
        /* Get server's response using InputStream */
        Reader input = new InputStreamReader(out.getInputStream());
        BufferedReader reader = new BufferedReader(input);

        String output = reader.readLine();
        return output;
    }

}
