package com.abheeth.mlops.resource;

import com.abheeth.mlops.exception.LinkedWorkspaceNotFoundException;
import com.abheeth.mlops.model.MLWorkspace;
import com.abheeth.mlops.model.MachineLearningModel;
import com.abheeth.mlops.store.DataStore;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Path("/models")
@Produces(MediaType.APPLICATION_JSON)
public class ModelResource {

    @GET
    public Response getModels(@QueryParam("status") String status) {
        Collection<MachineLearningModel> allModels = DataStore.models.values();
        if (status == null || status.isBlank()) {
            return Response.ok(allModels).build();
        }
        var filtered = allModels.stream()
                .filter(m -> status.equalsIgnoreCase(m.getStatus()))
                .collect(Collectors.toList());
        return Response.ok(filtered).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createModel(MachineLearningModel model) {
        String workspaceId = model.getWorkspaceId();
        MLWorkspace workspace = DataStore.workspaces.get(workspaceId);
        if (workspace == null) {
            throw new LinkedWorkspaceNotFoundException(
                "Workspace " + workspaceId + " does not exist"
            );
        }
        String id = UUID.randomUUID().toString();
        model.setId(id);
        DataStore.models.put(id, model);
        workspace.getModelIds().add(id);
        return Response.status(Response.Status.CREATED).entity(model).build();
    }

    @Path("/{modelId}/metrics")
    public EvaluationMetricResource getMetrics(@PathParam("modelId") String modelId) {
        MachineLearningModel model = DataStore.models.get(modelId);
        if (model == null) {
            return null;
        }
        return new EvaluationMetricResource(modelId);
    }
}
