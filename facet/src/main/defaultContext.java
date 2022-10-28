package main;

public class defaultContext implements Context{
    @Override
    public Object validateComponent(Class interfaceClass, Object delegate){
        return delegate.getClass().equals(interfaceClass);
    }
    @Override
    public boolean validateInterface(Class interfaceClass) {
        if(interfaceClass.equals(securityMethods.class)){
            return accessController.checkPermission(new administratorPermission());
        }else return false;

    }
}
