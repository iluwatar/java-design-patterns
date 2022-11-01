package main;

public interface Context {

    /**
     * @param interfaceClass class of the interface
     * @param delegate object that can delegate
     * @return true if the interface can be delegated by the object
     */
    public Object validateComponent(Class interfaceClass, Object delegate);

    /**
     * @param interfaceClass class of the interface
     * @return true if the interface is valid
     */
    public boolean validateInterface(Class interfaceClass);
}