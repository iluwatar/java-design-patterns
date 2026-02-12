// Singleton Pattern Example
public class Singleton {
    // Step 1: Create a private static instance of the same class
    private static Singleton instance;

    // Step 2: Make the constructor private so no one can instantiate directly
    private Singleton() {
        System.out.println("Singleton instance created!");
    }

    // Step 3: Provide a public static method to get the instance
    public static Singleton getInstance() {
        if (instance == null) {
            // Lazy initialization
            instance = new Singleton();
        }
        return instance;
    }

    // Example method
    public void showMessage() {
        System.out.println("Hello from Singleton Pattern!");
    }

    // Test it
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();

        s1.showMessage();

        // Checking if both objects are same
        System.out.println("Are both instances same? " + (s1 == s2));
    }
}
