package com.abheeth.mlops.model;

public class EvaluationMetric {

    public EvaluationMetric() {
    }

    private String id;
    private long timestamp;
    private double accuracyScore;

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getAccuracyScore() {
        return accuracyScore;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setAccuracyScore(double accuracyScore) {
        this.accuracyScore = accuracyScore;
    }

}