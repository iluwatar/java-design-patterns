package com.iluwatar.InheritanceMapper;

import com.iluwatar.InheritanceMapper.ClassObject.Bowler;
import com.iluwatar.InheritanceMapper.ClassObject.Cricketer;
import com.iluwatar.InheritanceMapper.ClassObject.Footballer;




/**Inheritance mapper pattern.
 * map each instance of class base on the inheritance type chosen
 * To choose inheritance type go to ./ClassObject/Player.java
 */
final class App {
    /**
     * Program main entry point.
     *
     * @param args program runtime arguments
     */
    public static void main(final String[] args) {
       PlayerMapper pm = new PlayerMapper();
       Footballer f = new Footballer();
       f.setClub("lost apple tree");
       f.setName("lost apple");
       pm.insert(f);
       Bowler b = new Bowler();
       b.setName("lost banana");
       final double battlingAverage = 9;
       final double bowlingAverage = 9;
       b.setBattlingAverage(battlingAverage);
       b.setBowlingAverage(bowlingAverage);
       pm.insert(b);
       Cricketer c = new Cricketer();
       c.setName("lost coconut");
       final double battlingAverage2 = 9;
       c.setBattlingAverage(battlingAverage2);
       pm.insert(c);

    }
    /**
     * private constructor so class App can't be instantiated.
     */
    private App() { }
}
