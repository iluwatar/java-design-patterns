package main;

public interface Sentry {
    Context context = null;

    /**
     * @param interfaceClass class of the interface
     * @return true if the interface is valid
     */
    public boolean execute(Class interfaceClass);
}
