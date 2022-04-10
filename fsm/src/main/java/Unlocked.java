import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Unlocked extends AState {
    public Unlocked(CoinMachine entity) {
        super(entity,"Unlocked");
    }
    @Override
    public void pass() {
        setEntityState(new Locked(entity));
    }

    @Override
    public void coin() {

    }

    @Override
    public void failed() {
        setEntityState(new Broken(entity));
    }

    @Override
    public void fixed() {

    }
}
