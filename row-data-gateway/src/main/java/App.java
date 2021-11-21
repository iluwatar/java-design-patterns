import ParrotDataModel.OwnedParrot;
import ParrotRowGateWay.OwnedParrotFinder;
import ParrotRowGateWay.OwnedParrotGateWay;
import ParrotRowGateWay.ParrotTypeRegistry;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        // Getting an existing parrot and printing information
        OwnedParrot myOldParrot = new OwnedParrot();

        OwnedParrotFinder finder = new OwnedParrotFinder();
        OwnedParrotGateWay ownedParrotGateWay = finder.findById(1);

        myOldParrot.setOwnedParrotId(ownedParrotGateWay.getOwnedParrotId());
        myOldParrot.setParrotTypeId(ownedParrotGateWay.getParrotTypeId());
        myOldParrot.setParrotName(ownedParrotGateWay.getParrotName());
        myOldParrot.setColor(ownedParrotGateWay.getColor());
        myOldParrot.setParrotAge(ownedParrotGateWay.getParrotAge());
        myOldParrot.setTamed(ownedParrotGateWay.getTamed());

        myOldParrot.printParrotInformation();

        // Insert a new record into the DB
        System.out.println("Types of parrots mapping id to species:");
        ParrotTypeRegistry.printParrotTypeInformation();
        OwnedParrot myNewParrot = new OwnedParrot();
        myNewParrot.setParrotTypeId(1);
        myNewParrot.setParrotName("Mikey");
        myNewParrot.setParrotAge(1);
        myNewParrot.setColor("Blue and Gold");
        myNewParrot.setTamed(true);

        OwnedParrotGateWay myNewParrotGateWay = new OwnedParrotGateWay(myNewParrot);
        myNewParrotGateWay.insert();
        myNewParrot.setOwnedParrotId(myNewParrotGateWay.getOwnedParrotId());
        myNewParrot.printParrotInformation();


    }
}
