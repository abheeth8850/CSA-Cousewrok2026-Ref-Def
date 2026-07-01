package com.abheeth.mlops.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> discover() {
        return Map.of(
            "service", "MLOps API",
            "version", "1.0",
            "contact", Map.of(
                "name", "API Support",
                "email", "admin@mlops.local"
            ),
            "resources", Map.of(
                "workspaces", "/workspaces",
                "models", "/models"
            )
        );
    }
}