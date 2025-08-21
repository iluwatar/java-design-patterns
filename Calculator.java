public class Calculator {
    public int add(int a, int b) { return a + b; }
    public int subtract(int a, int b) { return a - b; }
    public int multiply(int a, int b) { return a * b; }
    public double divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("Divide by zero");
        return (double) a / b;
    }
    public double squareRoot(double number) {
        if (number < 0) throw new IllegalArgumentException("Cannot calculate square root of negative number");
        return Math.sqrt(number);
    }
}
