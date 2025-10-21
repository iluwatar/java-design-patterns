public class Main {
    public static void main(String[] args) {

        Blacksmith blacksmith;

        // Orc weapons
        blacksmith = new OrcBlacksmith();
        Weapon weapon1 = blacksmith.manufactureWeapon(WeaponType.SPEAR);
        System.out.println("Orc Blacksmith manufactured: " + weapon1.getName());

        Weapon weapon2 = blacksmith.manufactureWeapon(WeaponType.AXE);
        System.out.println("Orc Blacksmith manufactured: " + weapon2.getName());

        // Elf weapons
        blacksmith = new ElfBlacksmith();
        Weapon weapon3 = blacksmith.manufactureWeapon(WeaponType.SPEAR);
        System.out.println("Elf Blacksmith manufactured: " + weapon3.getName());

        Weapon weapon4 = blacksmith.manufactureWeapon(WeaponType.AXE);
        System.out.println("Elf Blacksmith manufactured: " + weapon4.getName());
    }
}
