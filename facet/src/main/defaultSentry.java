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
    public boolean execute(Client client, Class interfaceClass){
        this.context.setClient(client);
        if (this.context.validateInterface(interfaceClass)) {
            return true;
        } else {
            return false;
        }
    }
}
