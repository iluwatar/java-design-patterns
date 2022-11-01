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

    /**
     * @return a newly created Facet
     * @throws Exception when a new Facet cannot be created
     */
    public static Facet create() throws Exception{
        return new Facet();
    }

    /**
     * @param newSentry new sentry for the new Facet
     * @param newClasses new classes for the new Facet
     * @return a newly created Facet with assigned Sentry and classes
     * @throws Exception when a new Facet cannot be created
     */
    public static Facet create(Sentry newSentry, Class[] newClasses) throws Exception {
        return new Facet(newSentry, newClasses);
    }

    /**
     * @param f Facet to be queried
     * @param interfaces interfaces to be queried
     * @return all classes of queried Facet
     * @throws Exception when query fails
     */
    public static Class[] query(Facet f, Class[] interfaces) throws Exception {
        f.classes = interfaces;
        return f.classes;
    }

    /**
     * @param f Facet to be narrowed
     * @param interfaces interfaces used to be narrow the Facet
     * @return a narrowed Facet
     * @throws Exception when narrow fails
     */
    public static Facet narrow(Facet f, Class[] interfaces) throws Exception {
        f.classes = interfaces.clone();
        return f;
    }
}