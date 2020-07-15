package com.xebia.covid_app.models;

import java.util.List;

public class UserResponse {
    private String status;
    private String message;
    Payload PayloadObject;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Payload getPayload() {
        return PayloadObject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPayload(Payload payloadObject) {
        this.PayloadObject = payloadObject;
    }

    public class Payload {

        private List<?> objectList;
        private int totalTask;

        public int getTotalTask() {
            return totalTask;
        }

        public void setTotalTask(int totalTask) {
            this.totalTask = totalTask;
        }

        public List<?> getObjectList() {
            return objectList;
        }

        public void setObjectList(List<?> objectList) {
            this.objectList = objectList;
        }
    }
}



