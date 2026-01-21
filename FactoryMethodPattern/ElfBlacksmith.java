import java.util.HashMap;
import java.util.Map;

public class ElfBlacksmith implements Blacksmith {

    private static final Map<WeaponType, Weapon> ELF_ARSENAL = new HashMap<>();
    
    static {
        ELF_ARSENAL.put(WeaponType.SPEAR, new Weapon("Elven Spear"));
        ELF_ARSENAL.put(WeaponType.AXE, new Weapon("Elven Axe"));
    }

    @Override
    public Weapon manufactureWeapon(WeaponType weaponType) {
        return ELF_ARSENAL.get(weaponType);
    }
}
