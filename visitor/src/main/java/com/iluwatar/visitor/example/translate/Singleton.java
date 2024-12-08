package com.iluwatar.visitor.example.translate;

/**
 * 싱글턴 패턴.
 * 싱글턴 패턴은 클래스의 인스턴스가 하나만 존재하도록 보장하며,
 * 그 인스턴스에 대한 전역 접근 지점을 제공합니다.
 */
public class Singleton {

  // Singleton 클래스의 유일한 인스턴스
  private static Singleton 인스턴스;

  // 새로운 인스턴스 생성을 방지하기 위한 private 생성자
  private Singleton() {
    System.out.println("Singleton 인스턴스가 생성되었습니다.");
  }

  /**
   * Singleton 클래스의 유일한 인스턴스를 가져오는 메서드.
   * 인스턴스가 아직 생성되지 않은 경우, 이 시점에서 생성됩니다.
   *
   * @return Singleton의 유일한 인스턴스.
   */
  public static Singleton 가져오기() {
    if (인스턴스 == null) {
      인스턴스 = new Singleton();
    }
    return 인스턴스;
  }

  /**
   * Singleton 인스턴스에서 호출 가능한 메서드 예제.
   */
  public void 작업하기() {
    System.out.println("Singleton 인스턴스가 작동 중입니다.");
  }

  // Singleton 패턴을 테스트하기 위한 메인 메서드
  public static void main(String[] args) {
    Singleton singleton = Singleton.가져오기();
    singleton.작업하기();
  }
}
