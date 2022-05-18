import lombok.extern.slf4j.Slf4j;
@Slf4j

public abstract class AState implements translation{
    CoinMachine entity;
    private String name;
    public AState(CoinMachine entity, String name) {
        this.entity = entity;
        this.name=name;
    }
    void setEntityState(AState newState){
        entity.updateState(newState);
    }
    public String currentState(){
        return name;
    }
}
