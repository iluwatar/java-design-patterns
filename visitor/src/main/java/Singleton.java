public class Singleton {

  // Variable estática que contiene la instancia única de la clase
  private static Singleton instance;

  // Constructor privado para evitar la creación de múltiples instancias
  private Singleton() {
    // Constructor privado para prevenir la instanciación
  }

  // Método público que retorna la única instancia de la clase
  public static Singleton getInstance() {
    // Verifica si la instancia es nula y la crea si es necesario
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }

  // Método de ejemplo que representa la funcionalidad de la clase Singleton
  public void showMessage() {
    System.out.println("¡Hola desde Singleton!");
  }
}
