import java.util.HashMap;
import java.util.Map;

public class OrcBlacksmith implements Blacksmith {

    private static final Map<WeaponType, Weapon> ORC_ARSENAL = new HashMap<>();
    
    static {
        ORC_ARSENAL.put(WeaponType.SPEAR, new Weapon("Orcish Spear"));
        ORC_ARSENAL.put(WeaponType.AXE, new Weapon("Orcish Axe"));
    }

    @Override
    public Weapon manufactureWeapon(WeaponType weaponType) {
        return ORC_ARSENAL.get(weaponType);
    }
}
