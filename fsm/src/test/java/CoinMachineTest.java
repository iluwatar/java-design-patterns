

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CoinMachineTest {

    @Test
    public void testWorking(){
        CoinMachine coinMachine=new CoinMachine();
        assertEquals("Locked",coinMachine.currentState());
        coinMachine.pass();
        assertEquals("Locked",coinMachine.currentState());
        coinMachine.coin();
        assertEquals("Unlocked",coinMachine.currentState());
        coinMachine.coin();
        assertEquals("Unlocked",coinMachine.currentState());
        coinMachine.pass();
        assertEquals("Locked",coinMachine.currentState());
    }
    @Test
    public void testLockedToBroken(){
        CoinMachine coinMachine=new CoinMachine();
        coinMachine.fixed();
        assertEquals("Locked",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("Broken",coinMachine.currentState());
    }

    @Test
    public void testUnlockedToBroken(){
        CoinMachine coinMachine=new CoinMachine();
        coinMachine.coin();
        coinMachine.fixed();
        assertEquals("Unlocked",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("Broken",coinMachine.currentState());
    }

    @Test
    public void testRepair(){
        CoinMachine coinMachine=new CoinMachine();
        coinMachine.failed();
        assertEquals("Broken",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("Broken",coinMachine.currentState());
        coinMachine.coin();
        assertEquals("Broken",coinMachine.currentState());
        coinMachine.pass();
        assertEquals("Broken",coinMachine.currentState());
        coinMachine.failed();
        assertEquals("Broken",coinMachine.currentState());
        coinMachine.fixed();
        assertEquals("Locked",coinMachine.currentState());
    }
}
