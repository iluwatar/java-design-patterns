package main;

public class defaultContext implements Context{
    private Client client;

    public defaultContext(){
        client = new Client();
    }

    @Override
    public Object validateComponent(Class interfaceClass, Object delegate){
        return delegate.getClass().equals(interfaceClass);
    }
    @Override
    public boolean validateInterface(Class interfaceClass) {
        return interfaceClass.equals(securityMethods.class);
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
