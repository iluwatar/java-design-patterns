package com.iluwatar;

/**
 * 
 * Adapter (GnomeEngineer) converts the interface of the
 * target class (GoblinGlider) into suitable one expected
 * by the client (GnomeEngineeringManager).
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	GnomeEngineeringManager manager = new GnomeEngineeringManager();
    	manager.operateDevice();
    }
}
