package main;

public class accessController {

    /**
     * @param permission permission of the user (client permission/ administrator permission)
     * @return true if it is administrator permission
     */
    public static boolean checkPermission(Permission permission){
        assert permission != null;

        if(permission instanceof clientPermission)
            return false;
        else return permission instanceof administratorPermission;
    }
}
