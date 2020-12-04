package com.rejointech.tu_du.model;

public class TaskData {

    int ref;
    boolean status;
    String taskData;

    public TaskData() {
    }

    public TaskData(int ref, boolean status, String taskData) {
        this.ref = ref;
        this.status = status;
        this.taskData = taskData;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }
}
