/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arep.calculator;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

/**
 *
 * @author jorge.gamboa-s
 */
public class HttpServer {

    public static LinkedList<String> store = new LinkedList<String>();
    public static Double mean;

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(9001);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9001.");
            System.exit(1);
        }

        Socket clientSocket = null;

        boolean running = true;
        while (running) {
            String inputLine, outputLine;
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

            if (uriRequest.startsWith("/BACKEND/list")) {
                outputLine = hedleGetList(uriRequest);
            } else if (uriRequest.startsWith("/BACKEND/add")) {
                outputLine = hedleaddN(uriRequest);
            } else if (uriRequest.startsWith("/BACKEND/stats")) {
                outputLine = hedleStats(uriRequest);
            } else if (uriRequest.startsWith("/BACKEND/clear")) {
                outputLine = hedleClear(uriRequest);
            } else if (uriRequest.startsWith("/BACKEND/cliente")) {
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

    private static String hedleGetList(String uriRequest) {
        if (store.isEmpty()) {
            return "HTTP/1.1 409 Conflict\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "  \"status\": \"ERR\",\n"
                    + "  \"error\": \"invalid_number\"\n"
                    + "}";

        }

        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n"
                + "  \"status\": \"OK\",\n"
                + "  \"values\": " + store + "\n"
                + "}";
    }

    private static String hedleaddN(String uriRequest) throws URISyntaxException {
        URI uri = new URI(uriRequest);
        String number = uri.getQuery().split("=")[1];

        try {
            Double.valueOf(number);
            store.add(number);
            return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "  \"status\": \"OK\",\n"
                    + "  \"added\": " + number + ",\n"
                    + "  \"count\": " + store.size() + "\n"
                    + "}";
        } catch (NumberFormatException e) {
            return "HTTP/1.1 400 Bad Request\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "  \"status\": \"ERR\",\n"
                    + "  \"error\": \"invalid_number\"\n"
                    + "}";
        }

    }

    private static String hedleClear(String uriRequest) {
        store.clear();
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: application/json\r\n"
                + "\r\n"
                + "{\n"
                + "  \"status\": \"OK\",\n"
                + "  \"message\": \"list_cleared\"\n"
                + "}";

    }

    private static String hedleStats(String uriRequest) {

        if (store.isEmpty()) {
            return "HTTP/1.1 409 Conflict\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "  \"status\": \"ERR\",\n"
                    + "  \"error\": \"invalid_number\"\n"
                    + "}";

        }

        try {

            return "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "  \"status\": \"OK\",\n"
                    + "  \"mean\":" + mean() + ",\n"
                    + "  \"stddev\": " + stddev() + ",\n"
                    + "  \"count\": " + store.size() + "\n"
                    + "}";
        } catch (NumberFormatException e) {
            return "HTTP/1.1 409 Conflict\r\n"
                    + "Content-Type: application/json\r\n"
                    + "\r\n"
                    + "{\n"
                    + "  \"status\": \"ERR\",\n"
                    + "  \"error\": \"invalid_number\"\n"
                    + "}";
        }
    }

    private static String getStaticFile() {
        return null;
    }

    private static String mean() {
        Double suma = 0.0;
        Double count = 0.0;

        for (String s : store) {
            suma += Double.valueOf(s);
            count += 1;
        }
        Double result = suma / count;
        mean = result;
        return String.valueOf(result);

    }

    private static String stddev() {
        Double suma = 0.0;
        Double count = 0.0;

        for (String s : store) {
            suma += Math.pow(Double.valueOf(s) - mean, 2);
            count += 1;
        }
        count -= 1;

        Double result = Math.sqrt((1 / count) * suma);

        return String.valueOf(result);

    }

}
