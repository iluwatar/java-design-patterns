package com.iluwatar.SingleTableInheritance;

import com.iluwatar.SingleTableInheritance.ClassObject.Car;
import com.iluwatar.SingleTableInheritance.ClassObject.Freighter;
import com.iluwatar.SingleTableInheritance.ClassObject.Train;

/**Singletable inheritance pattern map
 * each instance of class in an inheritance tree into a single table.
 */
final class App {
    /**
     * Program main entry point.
     *
     * @param args program runtime arguments
     */
    public static void main(final String[] args) {
        VehicleMapper vm = new VehicleMapper();

        Car car = new Car();
        car.setManufacturer("Volkswagen");
        final int noOfPassengersCar = 4;
        final int engineCapacity = 1500;
        car.setNoOfPassengers(noOfPassengersCar);
        car.setEngineCapacity(engineCapacity);
        vm.insert(car);

        Train train = new Train();
        train.setManufacturer("CRRC");
        final int noOfPassengersTrain = 200;
        final int noOfCarriages = 10;
        train.setNoOfPassengers(noOfPassengersTrain);
        train.setNoOfCarriages(noOfCarriages);
        vm.insert(train);

        Freighter airplane = new Freighter();
        airplane.setManufacturer("Boeing");
        final int loadCapacity = 500;
        final int lengthOfPlane = 70;
        airplane.setLoadCapacity(loadCapacity);
        airplane.setLengthOfPlane(lengthOfPlane);
        vm.insert(airplane);

    }
    /**
     * private constructor so class App can't be instantiated.
     */
    private App() { }
}
