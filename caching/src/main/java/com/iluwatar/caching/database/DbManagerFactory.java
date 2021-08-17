package com.iluwatar.caching.database;

public class DbManagerFactory {
    public static DbManager initDb(boolean isMongo){
        if(isMongo){
            return new MongoDb();
        }
        return new VirtualDb();
    }
}
