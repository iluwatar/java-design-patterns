package main;

public class accessController {

    public static boolean checkPermission(Permission permission){
        assert permission != null;

        if(permission instanceof clientPermission)
            return false;
        else return permission instanceof administratorPermission;
    }
}
