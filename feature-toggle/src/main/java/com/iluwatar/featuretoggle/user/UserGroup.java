package com.iluwatar.featuretoggle.user;

import java.util.ArrayList;
import java.util.List;

public class UserGroup {

    private static List<User> freeGroup = new ArrayList<>();
    private static List<User> paidGroup = new ArrayList<>();

    public static void addUserToFreeGroup(final User user){
        if(paidGroup.contains(user)){
            throw new IllegalArgumentException("User all ready member of paid group.");
        }else{
            if(!freeGroup.contains(user)){
                freeGroup.add(user);
            }
        }
    }

    public static void addUserToPaidGroup(final User user){
        if(freeGroup.contains(user)){
            throw new IllegalArgumentException("User all ready member of free group.");
        }else{
            if(!paidGroup.contains(user)){
                paidGroup.add(user);
            }
        }
    }

    public static boolean isPaid(User user) {
        return paidGroup.contains(user);
    }
}
