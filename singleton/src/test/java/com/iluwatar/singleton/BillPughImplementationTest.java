package com.iluwatar.singleton;

/**
 * Bill Pugh 싱글턴 구현 테스트 클래스.
 */
public class BillPughImplementationTest extends SingletonTest<BillPughImplementation> {

  /**
   * 주어진 'getInstance' 메서드를 사용하여 싱글턴 테스트 인스턴스를 생성합니다.
   */
  public BillPughImplementationTest() {
    super(BillPughImplementation::getInstance);
  }
}
