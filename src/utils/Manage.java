package utils;

import enums.Status;

public interface Manage {
    public void insert();
    public Status getStatus(); //sarebbe il checkStatus sul diagramma
    public void modifyStatus();
}
