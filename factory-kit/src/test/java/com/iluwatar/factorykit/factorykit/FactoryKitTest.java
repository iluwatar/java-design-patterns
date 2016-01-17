package com.iluwatar.factorykit.factorykit;

import com.iluwatar.factorykit.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by crossy on 2016-01-16.
 */
public class FactoryKitTest {

    private WeaponFactory factory;

    @Before public void init() {
        factory = WeaponFactory.factory(builder -> {
            builder.add(WeaponType.SPEAR, Spear::new);
            builder.add(WeaponType.AXE, Axe::new);
            builder.add(WeaponType.SWORD, Sword::new);
        });
    }

    /**
     * Testing {@link WeaponFactory} to produce a SPEAR asserting that the Weapon is an instance of {@link Spear}
     */
    @Test public void testSpearWeapon() {
        Weapon weapon = factory.create(WeaponType.SPEAR);
        verifyWeapon(weapon, Spear.class);
    }

    /**
     * Testing {@link WeaponFactory} to produce a AXE asserting that the Weapon is an instance of {@link Axe}
     */
    @Test public void testAxeWeapon() {
        Weapon weapon = factory.create(WeaponType.AXE);
        verifyWeapon(weapon, Axe.class);
    }


    /**
     * Testing {@link WeaponFactory} to produce a SWORD asserting that the Weapon is an instance of {@link Sword}
     */
    @Test public void testWeapon() {
        Weapon weapon = factory.create(WeaponType.SWORD);
        verifyWeapon(weapon, Sword.class);
    }

    /**
     * This method asserts that the weapon object that is passed is an instance of the clazz
     *
     * @param weapon weapon object which is to be verified
     * @param clazz  expected class of the weapon
     */
    private void verifyWeapon(Weapon weapon, Class clazz) {
        assertTrue("Weapon must be an object of: " + clazz.getName(), clazz.isInstance(weapon));
    }
}
