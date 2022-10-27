package com.iluwatar.activeobject;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try{
            DB db = new DB("world","root","apple-trunks","localhost:3306", "city");

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
