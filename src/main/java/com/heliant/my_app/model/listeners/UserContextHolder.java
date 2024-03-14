package com.heliant.my_app.model.listeners;

import com.heliant.my_app.model.User;

public class UserContextHolder {

    private static final ThreadLocal<User> userIdThreadLocal = new ThreadLocal<>();

    public static void setUser(User user){
        userIdThreadLocal.set(user);
    }

    public static User getUser(){
        return userIdThreadLocal.get();
    }

    public static void clear(){
        userIdThreadLocal.remove();
    }
}
