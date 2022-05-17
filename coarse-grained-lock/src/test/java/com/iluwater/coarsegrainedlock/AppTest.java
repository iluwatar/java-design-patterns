package com.iluwater.coarsegrainedlock;

import com.iluwater.coarsegrainedlock.entity.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

  @Test
  void shouldExecuteApplicationWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }


}
