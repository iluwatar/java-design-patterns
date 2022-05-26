package com.iluwatar.InheritanceMapper;

import com.iluwatar.InheritanceMapper.ClassObject.Bowler;
import com.iluwatar.InheritanceMapper.ClassObject.Cricketer;
import com.iluwatar.InheritanceMapper.ClassObject.Footballer;
import com.iluwatar.InheritanceMapper.ClassObject.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for App class.
 */
//Run App.java before run the test cases
public class InheritanceMapperTest {
    @Test
    public void testFind(){
        PlayerMapper pm = new PlayerMapper();
        Player found1 = pm.find(1);
        Assert.assertNotNull(found1);
        Assert.assertEquals("lost apple", found1.getName());
        Player found2 = pm.find(2);
        Assert.assertNotNull(found2);
        Assert.assertEquals("lost banana", found2.getName());
        Player found3 = pm.find(3);
        Assert.assertNotNull(found3);
        Assert.assertEquals("lost coconut", found3.getName());
    }

    @Test
    public void testInsertFootballer(){
        PlayerMapper pm = new PlayerMapper();
        Footballer f = new Footballer();
        f.setName("Apple");
        f.setClub("Apple Tree");

        pm.insert(f);
        Footballer found = (Footballer) pm.find(f.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals("Apple", found.getName());
        Assert.assertEquals("Apple Tree", found.getClub());
    }

    @Test
    public void testInsertBowler(){
        PlayerMapper pm = new PlayerMapper();
        Bowler b = new Bowler();
        b.setName("banana");
        b.setBowlingAverage(2.1);
        b.setBattlingAverage(1.1);

        pm.insert(b);
        Bowler found = (Bowler) pm.find(b.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals("banana", found.getName());
        Assert.assertEquals(2.1, found.getBowlingAverage(), 0.1);
        Assert.assertEquals(1.1, found.getBattlingAverage(), 0.1);
    }

    @Test
    public void testInsertCricketer(){
        PlayerMapper pm = new PlayerMapper();
        Cricketer c = new Cricketer();
        c.setName("coconut");
        c.setBattlingAverage(5.1);

        pm.insert(c);
        Cricketer found = (Cricketer) pm.find(c.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals("coconut", found.getName());
        Assert.assertEquals(5.1, found.getBattlingAverage(), 0.1);
    }

    @Test
    public void testUpdateFootballer(){
        PlayerMapper pm = new PlayerMapper();
        Footballer f = new Footballer();
        f.setName("fake apple");
        f.setClub("fake Apple Tree");
        pm.insert(f);
        f.setName("green apple");
        f.setClub("green apple tree");
        pm.update(f);
        Footballer found = (Footballer) pm.find(f.getId());
        Assert.assertEquals("green apple", found.getName());
        Assert.assertEquals("green apple tree", found.getClub());

    }

    @Test
    public void testUpdateBowler(){
        PlayerMapper pm = new PlayerMapper();
        Bowler b = new Bowler();
        b.setName("fake banana");
        b.setBowlingAverage(-1);
        b.setBattlingAverage(-1);
        pm.insert(b);
        b.setName("green banana");
        b.setBowlingAverage(2.5);
        b.setBattlingAverage(1.5);
        pm.update(b);

        Bowler found = (Bowler) pm.find(b.getId());
        Assert.assertEquals("green banana", found.getName());
        Assert.assertEquals(2.5, found.getBowlingAverage(), 0.1);
        Assert.assertEquals(1.5, found.getBattlingAverage(), 0.1);
    }

    @Test
    public void testUpdateCricketer(){
        PlayerMapper pm = new PlayerMapper();
        Cricketer c = new Cricketer();
        c.setName("fake coconut");
        c.setBattlingAverage(-1);
        pm.insert(c);
        c.setName("green coconut");
        c.setBattlingAverage(5.5);
        pm.update(c);

        Cricketer found = (Cricketer) pm.find(c.getId());
        Assert.assertEquals("green coconut", found.getName());
        Assert.assertEquals(5.5, found.getBattlingAverage(), 0.1);

    }

    @Test
    public void testDeleteCricketer(){
        PlayerMapper pm = new PlayerMapper();
        Cricketer c = new Cricketer();
        pm.insert(c);
        pm.delete(c);
        Cricketer found = (Cricketer) pm.find(c.getId());
        Assert.assertNull(found);
    }

    @Test
    public void testDeleteFootballer(){
        PlayerMapper pm = new PlayerMapper();
        Footballer f = new Footballer();
        pm.insert(f);
        pm.delete(f);
        Footballer found = (Footballer) pm.find(f.getId());
        Assert.assertNull(found);
    }

    @Test
    public void testDeleteBowler(){
        PlayerMapper pm = new PlayerMapper();
        Bowler b = new Bowler();
        pm.insert(b);
        pm.delete(b);
        Bowler found = (Bowler) pm.find(b.getId());
        Assert.assertNull(found);
    }
}