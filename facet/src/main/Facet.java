package main;

public class Facet {
    private Sentry sentry;
    private Class[] classes;

    private Facet(){
        this.sentry = new defaultSentry();
        this.classes = new Class[0];
    }

    private Facet(Sentry sentry, Class[] classes){
        this.sentry = sentry;
        this.classes = classes;
    }

    public static Facet create() throws Exception{
        return new Facet();
    }
    public static Facet create(Sentry newSentry, Class[] newClasses) throws Exception {
        return new Facet(newSentry, newClasses);
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