public class App {
    public static void main(String[] args) {
        CoinMachine coinMachine=new CoinMachine();
        System.out.println(coinMachine.currentState());
        coinMachine.coin();
        System.out.println(coinMachine.currentState());
        coinMachine.pass();
        System.out.println(coinMachine.currentState());
        coinMachine.failed();
        System.out.println(coinMachine.currentState());
        coinMachine.fixed();
        System.out.println(coinMachine.currentState());
    }
}
