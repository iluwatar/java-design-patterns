

import com.iluwatar.fsm.CoinMachine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CoinMachineTest {

    @Test
    public void testWorking(){
        CoinMachine coinMachine=new CoinMachine();
        assertEquals("com.iluwatar.fsm.Locked",coinMachine.currentState());
        coinMachine.pass();
        assertEquals("com.iluwatar.fsm.Locked",coinMachine.currentState());
        coinMachine.coin();
        assertEquals("com.iluwatar.fsm.Unlocked",coinMachine.currentState());
        coinMachine.coin();
        assertEquals("com.iluwatar.fsm.Unlocked",coinMachine.currentState());
        coinMachine.pass();
        assertEquals("com.iluwatar.fsm.Locked",coinMachine.currentState());
    }
    @Test
    public void testLockedToBroken(){
        CoinMachine coinMachine=new CoinMachine();
        coinMachine.fixed();
        assertEquals("com.iluwatar.fsm.Locked",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("com.iluwatar.fsm.Broken",coinMachine.currentState());
    }

    @Test
    public void testUnlockedToBroken(){
        CoinMachine coinMachine=new CoinMachine();
        coinMachine.coin();
        coinMachine.fixed();
        assertEquals("com.iluwatar.fsm.Unlocked",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("com.iluwatar.fsm.Broken",coinMachine.currentState());
    }

    @Test
    public void testRepair(){
        CoinMachine coinMachine=new CoinMachine();
        coinMachine.failed();
        assertEquals("com.iluwatar.fsm.Broken",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("com.iluwatar.fsm.Broken",coinMachine.currentState());
        coinMachine.coin();
        assertEquals("com.iluwatar.fsm.Broken",coinMachine.currentState());
        coinMachine.pass();
        assertEquals("com.iluwatar.fsm.Broken",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("com.iluwatar.fsm.Broken",coinMachine.currentState());
        coinMachine.fixed();
        assertEquals("com.iluwatar.fsm.Locked",coinMachine.currentState());
    }
}
