package com.iluwatar.singleton;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

/**
 * <p>싱글턴 패턴 테스트 클래스.</p>
 *
 * <p>첫 번째 테스트는 동일한 스레드 내에서 getInstance 메서드가 항상 동일한 객체를 반환하는지 확인합니다.</p>
 * <p>두 번째 테스트는 서로 다른 스레드에서 getInstance 메서드가 동일한 객체를 반환하는지 확인합니다.</p>
 *
 * @param <S> 싱글턴을 생성하는 Supplier 메서드
 */
abstract class SingletonTest<S> {

  /**
   * 싱글턴 클래스의 getInstance 메서드를 제공하는 Supplier.
   */
  private final Supplier<S> 싱글턴인스턴스메서드;

  /**
   * 주어진 'getInstance' 메서드를 사용하여 싱글턴 테스트 인스턴스를 생성합니다.
   *
   * @param 싱글턴인스턴스메서드 싱글턴의 getInstance 메서드
   */
  public SingletonTest(final Supplier<S> 싱글턴인스턴스메서드) {
    this.싱글턴인스턴스메서드 = 싱글턴인스턴스메서드;
  }

  /**
   * 동일한 스레드에서 여러 번 호출 시 동일한 객체가 반환되는지 테스트합니다.
   */
  @Test
  void 동일_스레드_테스트() {
    // 동일한 스레드에서 여러 인스턴스를 생성
    var instance1 = this.싱글턴인스턴스메서드.get();
    var instance2 = this.싱글턴인스턴스메서드.get();
    var instance3 = this.싱글턴인스턴스메서드.get();

    // 동일한 인스턴스인지 확인
    assertSame(instance1, instance2, "첫 번째와 두 번째 인스턴스는 동일해야 합니다.");
    assertSame(instance1, instance3, "첫 번째와 세 번째 인스턴스는 동일해야 합니다.");
    assertSame(instance2, instance3, "두 번째와 세 번째 인스턴스는 동일해야 합니다.");
  }

  /**
   * 서로 다른 스레드에서 여러 번 호출 시 동일한 객체가 반환되는지 테스트합니다.
   */
  @Test
  void 다중_스레드_테스트() {
    assertTimeout(ofMillis(10000), () -> {
      // 10000개의 작업 생성, 각 작업에서 싱글턴 인스턴스를 생성
      final var 작업들 = IntStream.range(0, 10000)
          .<Callable<S>>mapToObj(i -> this.싱글턴인스턴스메서드::get)
          .collect(Collectors.toCollection(ArrayList::new));

      // 최대 8개의 동시 실행 스레드를 사용하여 작업 처리
      final var 실행서비스 = Executors.newFixedThreadPool(8);
      final var 결과들 = 실행서비스.invokeAll(작업들);

      // 모든 스레드가 완료될 때까지 대기
      final var 예상_인스턴스 = this.싱글턴인스턴스메서드.get();
      for (var 결과 : 결과들) {
        final var 인스턴스 = 결과.get();
        assertNotNull(인스턴스, "각 스레드에서 반환된 인스턴스는 null이 아니어야 합니다.");
        assertSame(예상_인스턴스, 인스턴스, "모든 스레드에서 동일한 인스턴스가 반환되어야 합니다.");
      }

      // Executor 종료
      실행서비스.shutdown();
    });
  }
}
