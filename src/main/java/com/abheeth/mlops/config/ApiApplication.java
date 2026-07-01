package com.abheeth.mlops.config;

import com.abheeth.mlops.resource.DiscoveryResource;
import com.abheeth.mlops.resource.ModelResource;
import com.abheeth.mlops.resource.WorkspaceResource;
import com.abheeth.mlops.filter.ApiLoggingFilter;
import com.abheeth.mlops.mapper.WorkspaceNotEmptyExceptionMapper;
import com.abheeth.mlops.mapper.LinkedWorkspaceNotFoundExceptionMapper;
import com.abheeth.mlops.mapper.ModelDeprecatedExceptionMapper;
import com.abheeth.mlops.mapper.GlobalExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

public class ApiApplication extends ResourceConfig {

    public ApiApplication() {
        register(DiscoveryResource.class);
        register(WorkspaceResource.class);
        register(ModelResource.class);
        register(ApiLoggingFilter.class);
        register(WorkspaceNotEmptyExceptionMapper.class);
        register(LinkedWorkspaceNotFoundExceptionMapper.class);
        register(ModelDeprecatedExceptionMapper.class);
        register(GlobalExceptionMapper.class);
    }
}
