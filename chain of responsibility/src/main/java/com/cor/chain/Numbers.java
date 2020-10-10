// This object will contain 2 numbers and a
// calculation to perform in the form of a String

public class Numbers {
    private int number1;
    private int number2;
    private String calculationWanted;

    public Numbers(int newNumber1, int newNumber2, String calcWanted){

        number1 = newNumber1;

        number2 = newNumber2;

        calculationWanted = calcWanted;

    }

     
    public int getNumber1(){ return number1; }

    public int getNumber2(){ return number2; }

    public String getCalcWanted(){ return calculationWanted; }

     
}
