package main;

public class Facet {
    private Sentry sentry;
    private Class[] classes;
    private Client client;

    public static Facet create() throws Exception {
        Facet f = new Facet();
        f.sentry = new defaultSentry();
        f.client = new Client();
        return f;
    }

    public static Class[] query(Facet f, Class[] interfaces) throws Exception {
        f.classes = interfaces;
        return f.classes;
    }

    public static Facet narrow(Facet f, Class[] interfaces) throws Exception {
        f.classes = interfaces.clone();
        return f;
    }
}