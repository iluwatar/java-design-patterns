package ParrotRowGateWay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class ParrotRegistry {

    private static Map<Integer, OwnedParrotGateWay> parrotRegistry = new HashMap<>();

    private ParrotRegistry() {
    }

    /**
     * Add Owned Parrot Gateway object to Registry
     * the registry is updated to limit database calls if we already called the record before
     *
     * @param ownedParrotGateWay
     */
    public static void addOwnedParrot(OwnedParrotGateWay ownedParrotGateWay) {
        if (ownedParrotGateWay != null && !parrotRegistry.containsKey(ownedParrotGateWay.getOwnedParrotId())) {
            parrotRegistry.put(ownedParrotGateWay.getOwnedParrotId(), ownedParrotGateWay);
        }
    }

    /**
     * This method removes from Parrot Registry when a GateWay object makes a call to the delete method
     *
     * @param ownedParrotGateWay
     */
    public static void removeOwnedParrot(OwnedParrotGateWay ownedParrotGateWay) {
        if (ownedParrotGateWay != null && !parrotRegistry.containsKey(ownedParrotGateWay.getOwnedParrotId())) {
            parrotRegistry.remove(ownedParrotGateWay.getOwnedParrotId());
        }
    }

    /**
     * Update the Parrot Registry once a GateWay object makes a call to the upate method
     *
     * @param ownedParrotGateWay
     */
    public static void updateOwnedParrot(OwnedParrotGateWay ownedParrotGateWay) {
        if (ownedParrotGateWay != null && parrotRegistry.containsKey(ownedParrotGateWay.getOwnedParrotId())) {
            parrotRegistry.put(ownedParrotGateWay.getOwnedParrotId(), ownedParrotGateWay);
        }
    }

    /**
     * Returns an Owned Parrot GateWay object if stored in Parrot Registry
     *
     * @param ownedParrotId
     * @return OwnedParrotGateWay
     */
    public static OwnedParrotGateWay getOwnedParrot(Integer ownedParrotId) {
        OwnedParrotGateWay myParrot = null;
        if (parrotRegistry.containsKey(ownedParrotId)) {
            myParrot = parrotRegistry.get(ownedParrotId);
        }

        return myParrot;
    }

    /**
     * Returns the size of the Parrot Registry
     *
     * @return Int
     */
    public static int getParrotRegistrySize() {
        return parrotRegistry.size();
    }

    /**
     * Returns a list of all Owned Parrot Gateway objects in Parrot Registry (memory)
     *
     * @return List<OwnedParrotGateWay>
     */
    public static List<OwnedParrotGateWay> getAllOwnedParrotsInRegistry() {
        List<OwnedParrotGateWay> ownedParrotGateWayList = new ArrayList<>();

        if (!parrotRegistry.isEmpty()) {
            ownedParrotGateWayList = parrotRegistry.values().stream().collect(Collectors.toList());
        }

        return ownedParrotGateWayList;
    }

}
