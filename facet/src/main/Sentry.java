package main;


public interface Sentry {
    Context context = null;
    public boolean execute(Client client, Class interfaceClass);
}
