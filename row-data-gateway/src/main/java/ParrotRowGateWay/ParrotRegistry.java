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

    public static void addOwnedParrot(OwnedParrotGateWay ownedParrotGateWay) {
        if (ownedParrotGateWay != null && !parrotRegistry.containsKey(ownedParrotGateWay.getOwnedParrotId())) {
            parrotRegistry.put(ownedParrotGateWay.getOwnedParrotId(), ownedParrotGateWay);
        }
    }

    public static void removeOwnedParrot(OwnedParrotGateWay ownedParrotGateWay) {
        if (ownedParrotGateWay != null && !parrotRegistry.containsKey(ownedParrotGateWay.getOwnedParrotId())) {
            parrotRegistry.remove(ownedParrotGateWay.getOwnedParrotId());
        }
    }

    public static void updateOwnedParrot(OwnedParrotGateWay ownedParrotGateWay) {
        if (ownedParrotGateWay != null && parrotRegistry.containsKey(ownedParrotGateWay.getOwnedParrotId())) {
            parrotRegistry.put(ownedParrotGateWay.getOwnedParrotId(), ownedParrotGateWay);
        }
    }

    public static OwnedParrotGateWay getOwnedParrot(Integer ownedParrotId) {
        OwnedParrotGateWay myParrot = null;
        if (parrotRegistry.containsKey(ownedParrotId)) {
            myParrot = parrotRegistry.get(ownedParrotId);
        }

        return myParrot;
    }

    public static int getParrotRegistrySize() {
        return parrotRegistry.size();
    }

    public static List<OwnedParrotGateWay> getAllOwnedParrotsInRegistry() {
        List<OwnedParrotGateWay> ownedParrotGateWayList = new ArrayList<>();

        if (!parrotRegistry.isEmpty()) {
            ownedParrotGateWayList = parrotRegistry.values().stream().collect(Collectors.toList());
        }

        return ownedParrotGateWayList;
    }

}
