package com.abheeth.mlops.mapper;

import com.abheeth.mlops.exception.LinkedWorkspaceNotFoundException;
import com.abheeth.mlops.model.ApiError;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LinkedWorkspaceNotFoundExceptionMapper implements ExceptionMapper<LinkedWorkspaceNotFoundException> {

    @Override
    public Response toResponse(LinkedWorkspaceNotFoundException exception) {
        return Response.status(422)
                .entity(new ApiError(422, exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
