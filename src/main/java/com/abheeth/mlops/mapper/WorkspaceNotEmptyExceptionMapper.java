package com.abheeth.mlops.mapper;

import com.abheeth.mlops.exception.WorkspaceNotEmptyException;
import com.abheeth.mlops.model.ApiError;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WorkspaceNotEmptyExceptionMapper implements ExceptionMapper<WorkspaceNotEmptyException> {

    @Override
    public Response toResponse(WorkspaceNotEmptyException exception) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ApiError(409, exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
