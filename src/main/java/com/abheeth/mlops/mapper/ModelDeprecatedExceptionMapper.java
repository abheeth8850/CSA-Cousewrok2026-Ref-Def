package com.abheeth.mlops.mapper;

import com.abheeth.mlops.exception.ModelDeprecatedException;
import com.abheeth.mlops.model.ApiError;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ModelDeprecatedExceptionMapper implements ExceptionMapper<ModelDeprecatedException> {

    @Override
    public Response toResponse(ModelDeprecatedException exception) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new ApiError(403, exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
