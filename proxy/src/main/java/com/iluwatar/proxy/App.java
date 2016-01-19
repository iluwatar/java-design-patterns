package com.iluwatar.proxy;

/**
 * 
 * A proxy, in its most general form, is a class functioning as an interface to something else. The
 * proxy could interface to anything: a network connection, a large object in memory, a file, or
 * some other resource that is expensive or impossible to duplicate. In short, a proxy is a wrapper
 * or agent object that is being called by the client to access the real serving object behind the
 * scenes.
 * <p>
 * The Proxy design pattern allows you to provide an interface to other objects by creating a
 * wrapper class as the proxy. The wrapper class, which is the proxy, can add additional
 * functionality to the object of interest without changing the object's code.
 * <p>
 * In this example the proxy ({@link WizardTowerProxy}) controls access to the actual object (
 * {@link WizardTower}).
 * 
 */
public class App {

  /**
   * Program entry point
   */
  public static void main(String[] args) {

    WizardTowerProxy tower = new WizardTowerProxy();
    tower.enter(new Wizard("Red wizard"));
    tower.enter(new Wizard("White wizard"));
    tower.enter(new Wizard("Black wizard"));
    tower.enter(new Wizard("Green wizard"));
    tower.enter(new Wizard("Brown wizard"));

  }
}
