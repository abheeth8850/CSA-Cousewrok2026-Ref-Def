package com.abheeth.mlops.model;

public class MachineLearningModel {

    private String id;
    private String framework;

    public void setId(String id) {
        this.id = id;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLatestAccuracy(double latestAccuracy) {
        this.latestAccuracy = latestAccuracy;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getId() {
        return id;
    }

    public String getFramework() {
        return framework;
    }

    public String getStatus() {
        return status;
    }

    public double getLatestAccuracy() {
        return latestAccuracy;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public MachineLearningModel() {
    }
    private String status;
    private double latestAccuracy;
    private String workspaceId;

}