package com.abheeth.mlops.model;

import java.util.ArrayList;
import java.util.List;

public class MLWorkspace {

    private String id;
    private String teamName;
    private int storageQuotaGb;
    private List<String> modelIds = new ArrayList<>();

    public String getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getStorageQuotaGb() {
        return storageQuotaGb;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setStorageQuotaGb(int storageQuotaGb) {
        this.storageQuotaGb = storageQuotaGb;
    }

    public void setModelIds(List<String> modelIds) {
        this.modelIds = modelIds;
    }

    public List<String> getModelIds() {
        return modelIds;
    }

    public MLWorkspace() {
    }

}