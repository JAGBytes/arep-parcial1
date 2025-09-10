/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

/**
 *
 * @author jorge.gamboa-s
 */
public class Fachada {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "http://localhost:9001/BACKEND";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9001.");
            System.exit(1);
        }

        Socket clientSocket = null;

        boolean running = true;
        while (running) {

            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            String uriRequest = "";
            boolean fistLine = true;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Recibí: " + inputLine);
                if (fistLine) {
                    fistLine = false;
                    uriRequest = inputLine.split(" ")[1];
                    System.out.println("fue recibida la uri request: " + uriRequest + ".");
                }

                if (!in.ready()) {
                    break;
                }
            }
            if (uriRequest.startsWith("/list") || uriRequest.startsWith("/add")
                    || uriRequest.startsWith("/clear") || uriRequest.startsWith("/stats")) {
                outputLine = HttpConnection(uriRequest);

            } else if (uriRequest.startsWith("/cliente")) {
                outputLine = getStaticFile();
            } else {
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + "{\n"
                        + "  \"status\": \"ERR\",\n"
                        + "  \"error\": \"backend_unreachable\"\n"
                        + "}";

            }
            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    public static String HttpConnection(String path) throws IOException {

        URL obj = new URL(GET_URL + path);
        System.out.println(GET_URL + path);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
            return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n" + response.toString();
        } else {
            System.out.println("GET request not worked");

            return "HTTP/1.1 502 Bad Gateway\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "  \"status\": \"ERR\",\n"
                    + "  \"error\": \"backend_unreachable\"\n"
                    + "}";
        }

    }

    private static String getStaticFile() {
        return null;
    }
}
