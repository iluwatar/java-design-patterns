package com.iluwatar.bloc;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mockStatic;


class MainTest {

  @Test
  void testMain() {
    try (var mockedBlocUi = mockStatic(BlocUi.class)) {
      // Call the main method
      Main.main(new String[]{});

      // Verify that createAndShowUi was called
      mockedBlocUi.verify(() -> new BlocUi().createAndShowUi());
    }
  }
}