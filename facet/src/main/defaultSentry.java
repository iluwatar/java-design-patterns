package main;

public class defaultSentry implements Sentry{
    private Context context;

    public defaultSentry(){
        context = new defaultContext();
    }
    public defaultSentry(Context context) {

        this.context = context;
    }

    @Override
    public boolean execute( Class interfaceClass){
        return this.context.validateInterface(interfaceClass);
    }
}
