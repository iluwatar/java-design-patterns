public class CoinMachine implements translation{
    private AState machineState=new Locked(this);
    public void updateState(AState newState){
        machineState=newState;
    }
    @Override
    public void pass() {
        machineState.pass();
    }

    @Override
    public void coin() {
        machineState.coin();
    }

    @Override
    public void failed() {
        machineState.failed();
    }

    @Override
    public void fixed() {
        machineState.fixed();
    }

    public String currentState(){
        return machineState.currentState();
    }
}
