package com.iluwater.component;

import com.iluwater.component.component.GraphicComponent;
import com.iluwater.component.component.HealthComponent;
import com.iluwater.component.component.PhysicComponent;
import com.iluwater.component.model.Component;
import com.iluwater.component.model.Vector2d;

final class App {

    private App() { }
    /**
     * Program main entry point.
     *
     * @param args program runtime arguments
     */
    public static void main(final String[] args) throws Exception {
        System.out.println("Create Game Objects");
        // Create 1 Main Hero
        final float healthNinja = 95f;
        GameObject cowNinja = new GameObject(
                "JackCow", healthNinja,
                new Vector2d(0.0f, 0.0f)); // Great health
        cowNinja.addComponent(new GraphicComponent());
        cowNinja.addComponent(new HealthComponent());
        cowNinja.addComponent(new PhysicComponent());

        // Create 2 Enemies
        final float healthMonster1 = 3f;
        GameObject monster01 = new GameObject(
                "Monster1", healthMonster1,
                new Vector2d(2.0f, 2.0f)); // Poor health
        monster01.addComponent(new GraphicComponent());
        monster01.addComponent(new HealthComponent());
        monster01.addComponent(new PhysicComponent());

        final float healthMonster2 = 25f;
        GameObject monster02 = new GameObject(
                "Monster2", healthMonster2,
                new Vector2d(1.0f, 1.0f)); // Normal health
        monster02.addComponent(new GraphicComponent());
        monster02.addComponent(new HealthComponent());
        monster02.addComponent(new PhysicComponent());

        //Loop and update the Game Object in a scene.
        System.out.println("\n*** Show CowNinja Status ***");
        cowNinja.update();

        System.out.println("\n*** Show Monster Enemy Status ***");
        monster01.update();
        monster02.update();

        // Get specific
        System.out.println("\n*** Get Specific Component from Game Object ***");
        Component cowNinjaHealthComponent = cowNinja.getComponent(
                HealthComponent.class);
        if (null != cowNinjaHealthComponent) {
            System.out.printf(
                    "cowNinja_HealthComponent - %s%n",
                    cowNinjaHealthComponent.getName());
        }

        Component monster2PhysicComponent = cowNinja.getComponent(
                PhysicComponent.class);
        if (null != monster2PhysicComponent) {
            System.out.printf(
                    "monster2_PhysicComponent - %s%n",
                    monster2PhysicComponent.getName());
        }
    }
}
