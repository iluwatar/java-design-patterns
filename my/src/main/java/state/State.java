package state;

import java.util.Random;

class Test {
    public static void main(String[] args) {
        Pay pay = new Pay();
        for (int i = 0; i < 10; i++) {
            pay.next();
        }
    }
}

public interface State {
    void onEnterState();

}

class UnPayState implements State {

    @Override
    public void onEnterState() {
        System.out.println("进入到：未支付状态");
    }
}

class PaySuccessState implements State {
    @Override
    public void onEnterState() {
        System.out.println("进入到：支付成功状态");
    }
}

class PayFailState implements State {
    @Override
    public void onEnterState() {
        System.out.println("进入到：支付失败状态");
    }
}

// 支付：未支付，支付成功，支付失败
class Pay {
    private State state = new UnPayState();

    public void next() {
        if (state instanceof UnPayState) {
            // 未支付
            changeStateTo(new Random().nextBoolean() ? new PaySuccessState() : new PayFailState());
        } else if (state instanceof PayFailState) {
            // 支付失败
            changeStateTo(new Random().nextBoolean() ? new PaySuccessState() : new PayFailState());
        } else {
            // 支付成功，继续失败，以继续测试循环操作
            changeStateTo(new UnPayState());
        }
    }

    private void changeStateTo(State state) {
        this.state = state;
        state.onEnterState();
    }

}