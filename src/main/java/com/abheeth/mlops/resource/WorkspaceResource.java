package com.abheeth.mlops.resource;

import com.abheeth.mlops.exception.WorkspaceNotEmptyException;
import com.abheeth.mlops.model.MLWorkspace;
import com.abheeth.mlops.store.DataStore;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.UUID;

@Path("/workspaces")
@Produces(MediaType.APPLICATION_JSON)
public class WorkspaceResource {

    @GET
    public Response getWorkspaces() {
        return Response.ok(DataStore.workspaces.values()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createWorkspace(MLWorkspace workspace) {
        String id = UUID.randomUUID().toString();
        workspace.setId(id);
        workspace.setModelIds(new ArrayList<>());
        DataStore.workspaces.put(id, workspace);
        return Response.status(Response.Status.CREATED).entity(workspace).build();
    }

    @GET
    @Path("/{workspaceId}")
    public Response getWorkspace(@PathParam("workspaceId") String workspaceId) {
        MLWorkspace workspace = DataStore.workspaces.get(workspaceId);
        if (workspace == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(workspace).build();
    }

    @DELETE
    @Path("/{workspaceId}")
    public Response deleteWorkspace(@PathParam("workspaceId") String workspaceId) {
        MLWorkspace workspace = DataStore.workspaces.get(workspaceId);
        if (workspace == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (!workspace.getModelIds().isEmpty()) {
            throw new WorkspaceNotEmptyException(
                "Workspace " + workspaceId + " cannot be deleted because it contains " +
                workspace.getModelIds().size() + " model(s)"
            );
        }
        DataStore.workspaces.remove(workspaceId);
        return Response.noContent().build();
    }
}
