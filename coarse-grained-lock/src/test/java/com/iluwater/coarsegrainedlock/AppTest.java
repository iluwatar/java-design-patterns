package com.iluwater.coarsegrainedlock;

import com.iluwater.coarsegrainedlock.decisions.Cleveland;
import com.iluwater.coarsegrainedlock.decisions.LosAngeles;
import com.iluwater.coarsegrainedlock.decisions.Miami;
import com.iluwater.coarsegrainedlock.entity.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

  /**
   * Test whether the App example could run as expected.
   */
  @Test
  void shouldExecuteApplicationWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }

  /**
   * Test whether the number of champion could be added correctly.
   */
  @Test
  void testWinChampion(){
    Player russelWestbrook=new Player("Westbrook","Russel","Oklahoma City","Oklahoma");
    russelWestbrook.winChampion();
    assertEquals(1,russelWestbrook.getChampions());
  }

  /**
   * Test whether the Cleveland thread could be run normally.
   */
  @Test
  void testCleveland(){
    Player lebronJames=new Player("James","Lebron","Miami","Florida");
    Cleveland cleveland=new Cleveland(lebronJames);
    cleveland.start();
    assertDoesNotThrow(()->cleveland.join());
    assertEquals("Cleveland",lebronJames.getCity());
    assertEquals("Ohio",lebronJames.getState());
  }


  /**
   * Test whether the Miami thread could be run normally.
   */
  @Test
  void testMiami(){
    Player lebronJames=new Player("James","Lebron","Cleveland","Ohio");
    Miami miami=new Miami(lebronJames);
    miami.start();
    assertDoesNotThrow(()->miami.join());
    assertEquals("Miami",lebronJames.getCity());
    assertEquals("Florida",lebronJames.getState());
  }

  /**
   * Test whether the LosAngeles thread could be run normally.
   */
  @Test
  void testLosAngeles(){
    Player lebronJames=new Player("James","Lebron","Cleveland","Ohio");
    LosAngeles losAngeles=new LosAngeles(lebronJames);
    losAngeles.start();
    assertDoesNotThrow(()->losAngeles.join());
    assertEquals("LosAngeles",lebronJames.getCity());
    assertEquals("California",lebronJames.getState());
  }
}
