package com.iluwatar;

/**
 * 
 * Adapter (GnomeEngineerAdapter) converts the interface of the
 * target class (GoblinGlider) into suitable one.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	GnomeEngineerAdapter engineer = new GnomeEngineerAdapter();
    	engineer.flyGoblinGlider();
    }
}
