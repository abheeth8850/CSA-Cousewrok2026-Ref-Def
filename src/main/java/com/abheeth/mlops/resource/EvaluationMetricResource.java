package com.abheeth.mlops.resource;

import com.abheeth.mlops.exception.ModelDeprecatedException;
import com.abheeth.mlops.model.EvaluationMetric;
import com.abheeth.mlops.model.MachineLearningModel;
import com.abheeth.mlops.store.DataStore;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
public class EvaluationMetricResource {

    private final String modelId;

    public EvaluationMetricResource(String modelId) {
        this.modelId = modelId;
    }

    @GET
    public Response getMetrics() {
        List<EvaluationMetric> metrics = DataStore.metrics.get(modelId);
        if (metrics == null) {
            return Response.ok(new ArrayList<>()).build();
        }
        return Response.ok(metrics).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMetric(EvaluationMetric metric) {
        MachineLearningModel model = DataStore.models.get(modelId);
        if (model == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if ("DEPRECATED".equalsIgnoreCase(model.getStatus())) {
            throw new ModelDeprecatedException(
                "Model " + modelId + " is deprecated and cannot accept new metrics"
            );
        }
        String id = UUID.randomUUID().toString();
        metric.setId(id);
        metric.setTimestamp(System.currentTimeMillis());
        DataStore.metrics.computeIfAbsent(modelId, k -> new ArrayList<>()).add(metric);
        model.setLatestAccuracy(metric.getAccuracyScore());
        return Response.status(Response.Status.CREATED).entity(metric).build();
    }
}
