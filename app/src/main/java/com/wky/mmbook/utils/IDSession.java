package com.wky.mmbook.utils;

public class IDSession {
    private int UserId;
    private int AccId;

    public int getAccId() {
        return AccId;
    }

    public void setAccId(int accId) {
        AccId = accId;
    }

    private static IDSession instance;

    public int getUserId() {
        return UserId;
    }
    public static synchronized IDSession getInstance() {
        if (instance == null) {
            instance = new IDSession();
        }
        return instance;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public IDSession() {
    }
}
