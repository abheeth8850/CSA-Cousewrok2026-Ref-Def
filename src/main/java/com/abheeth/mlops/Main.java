package com.abheeth.mlops;

import com.abheeth.mlops.config.ApiApplication;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

public class Main {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
      return GrizzlyHttpServerFactory.createHttpServer(
        URI.create(BASE_URI + "/api/v1"),
        new ApiApplication()
);
    }

    public static void main(String[] args) {

        HttpServer server = startServer();

        System.out.println("=================================");
        System.out.println("Server Started");
       System.out.println("API URL: " + BASE_URI + "/api/v1");
        System.out.println("Press Ctrl + C to stop");
        System.out.println("=================================");

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            System.out.println("Server stopped.");
        }
    }
}