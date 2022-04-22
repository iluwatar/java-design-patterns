package com.iluwatar.SingleTableInheritance;

import com.iluwatar.SingleTableInheritance.ClassObject.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for App class.
 */
//Run App.java before run the test cases
public class SingleTableInheritanceTest {

    @Test(expected = NullPointerException.class)
    public void TestNoMapper(){
        VehicleMapper vm = new VehicleMapper();
        vm.insert(null);
    }

    @Test
    public void testfind(){
        VehicleMapper vm = new VehicleMapper();
        Vehicle v = vm.find(1);
        Assert.assertEquals("Car", v.getClass().getSimpleName());
        v = vm.find(2);
        Assert.assertEquals("Train", v.getClass().getSimpleName());
        v = vm.find(3);
        Assert.assertEquals("Freighter", v.getClass().getSimpleName());
        v = vm.find(-1);
        Assert.assertNull(v);
    }

    @Test
    public void testdelete(){
        VehicleMapper vm = new VehicleMapper();
        Vehicle c = vm.insert(new Car());
        vm.delete(c);
        Vehicle v = vm.find(c.getIdVehicle());
        Assert.assertNull(v);
    }

    @Test
    public void testupdateTrain(){
        VehicleMapper vm = new VehicleMapper();
        Train t = (Train) vm.insert(new Train());
        Train inserted = (Train) vm.find(t.getIdVehicle());
        Assert.assertEquals(0, inserted.getNoOfCarriages());
        t.setNoOfCarriages(20);
        vm.update(t);
        Train updated = (Train) vm.find(t.getIdVehicle());
        Assert.assertEquals(20, updated.getNoOfCarriages());
    }

    @Test
    public void testupdateCar(){
        VehicleMapper vm = new VehicleMapper();

        Car t = (Car) vm.insert(new Car());
        Car inserted = (Car) vm.find(t.getIdVehicle());
        Assert.assertEquals(0, inserted.getEngineCapacity());
        t.setEngineCapacity(20);
        vm.update(t);
        Car updated = (Car) vm.find(t.getIdVehicle());
        Assert.assertEquals(20, updated.getEngineCapacity());
    }

    @Test
    public void testupdateFrieghter(){
        VehicleMapper vm = new VehicleMapper();

        Freighter t = (Freighter) vm.insert(new Freighter());
        Freighter inserted = (Freighter) vm.find(t.getIdVehicle());
        Assert.assertEquals(0, inserted.getLengthOfPlane());
        t.setLengthOfPlane(20);
        vm.update(t);
        Freighter updated = (Freighter) vm.find(t.getIdVehicle());
        Assert.assertEquals(20, updated.getLengthOfPlane());
    }

    @Test
    public void testupdateNoVehicleFound(){
        VehicleMapper vm = new VehicleMapper();
        Freighter t = (Freighter) vm.insert(new Freighter());
        vm.delete(t);
        Vehicle v = vm.update(t);
        Assert.assertNull(v);
    }
    
    
    @Test
    public void testInsertCar(){
        VehicleMapper vm = new VehicleMapper();
        Car c = new Car();
        vm.insert(c);
        Vehicle v = vm.find(c.getIdVehicle());
        Assert.assertNotNull(v);
    }

    @Test
    public void testInsertTrain(){
        VehicleMapper vm = new VehicleMapper();
        Train c = new Train();
        vm.insert(c);
        Vehicle v = vm.find(c.getIdVehicle());
        Assert.assertNotNull(v);
    }

    @Test
    public void testInsertFreighter(){
        VehicleMapper vm = new VehicleMapper();
        Freighter c = new Freighter();
        vm.insert(c);
        Vehicle v = vm.find(c.getIdVehicle());
        Assert.assertNotNull(v);
    }


}