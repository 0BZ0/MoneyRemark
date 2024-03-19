package com.wky.mmbook.utils;

public class UserIDSession {
    private int UserId;
    private static UserIDSession instance;

    public int getUserId() {
        return UserId;
    }
    public static synchronized UserIDSession getInstance() {
        if (instance == null) {
            instance = new UserIDSession();
        }
        return instance;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public UserIDSession() {
    }
}
