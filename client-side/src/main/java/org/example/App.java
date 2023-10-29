package org.example;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        RegistrationServiceProxy registrationServiceProxy = new RegistrationServiceProxy();
        registrationServiceProxy.registerUser();
    }
}
