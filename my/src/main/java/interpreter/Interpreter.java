package interpreter;

class Test {
    public static void main(String[] args) {
        Interpreter interpreter1 = new NumberInterpreter(2);
        Interpreter interpreter2 = new NumberInterpreter(3);
        Interpreter interpreter3 = new PlusInterpreter(interpreter1, interpreter2);
        Interpreter interpreter4 = new MinusInterpreter(interpreter3, interpreter2);
        System.out.println(interpreter4.interpret());
    }
}

// 解释器
public interface Interpreter {
    int interpret();
}

class NumberInterpreter implements Interpreter {
    private final int value;

    public NumberInterpreter(int value) {
        this.value = value;
    }

    @Override
    public int interpret() {
        return value;
    }
}

// 减法表达式
class MinusInterpreter implements Interpreter {

    private final Interpreter leftExpression;
    private final Interpreter rightExpression;

    public MinusInterpreter(Interpreter leftExpression, Interpreter rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public int interpret() {
        return leftExpression.interpret() - rightExpression.interpret();
    }

    @Override
    public String toString() {
        return "-";
    }

}

// 加法表达式
class PlusInterpreter implements Interpreter {

    private final Interpreter leftExpression;
    private final Interpreter rightExpression;

    public PlusInterpreter(Interpreter leftExpression, Interpreter rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public int interpret() {
        return leftExpression.interpret() + rightExpression.interpret();
    }

    @Override
    public String toString() {
        return "+";
    }

}