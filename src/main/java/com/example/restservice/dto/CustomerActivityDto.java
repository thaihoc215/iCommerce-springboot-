package com.example.restservice.dto;

public class CustomerActivityDto {

    private long id;
    private long customerId;
    private String action;
    private long actionTime;

    public CustomerActivityDto() {
    }

    public CustomerActivityDto(long customerId, String action, long actionTime) {
        this.customerId = customerId;
        this.action = action;
        this.actionTime = actionTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public long getActionTime() {
        return actionTime;
    }

    public void setActionTime(long actionTime) {
        this.actionTime = actionTime;
    }
}
