package main;

public interface Context {

    public Object validateComponent(Class interfaceClass, Object delegate);
    public boolean validateInterface(Class interfaceClass);
}