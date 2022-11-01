package main;

public class defaultSentry implements Sentry{
    private Context context;

    public defaultSentry(){
        context = new defaultContext();
    }
    public defaultSentry(Context context) {

        this.context = context;
    }

    /**
     * @param interfaceClass class of the interface
     * @return true if the interface is valid for the context
     */
    @Override
    public boolean execute( Class interfaceClass){
        return this.context.validateInterface(interfaceClass);
    }
}
