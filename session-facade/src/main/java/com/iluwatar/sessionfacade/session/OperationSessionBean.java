package com.iluwatar.sessionfacade.session;
import com.iluwatar.sessionfacade.entity.Addition;
import com.iluwatar.sessionfacade.entity.Division;
import com.iluwatar.sessionfacade.entity.Multiplication;
import com.iluwatar.sessionfacade.entity.Subtraction;

import javax.ejb.*;

/**
 * To simplify the process, we use this class to represent a stateless session bean in a EJB project.
 * Methods in this class represents the business logics.
 */
@Stateless
public class OperationSessionBean implements OperationSessionBeanRemote {

    @Override
    public double add(double x, double y) {
        return new Addition(x, y).evaluate();
    }

    @Override
    public double subtract(double x, double y) {
        return new Subtraction(x, y).evaluate();
    }

    @Override
    public double multiply(double x, double y) {
        return new Multiplication(x, y).evaluate();
    }

    @Override
    public double divide(double x, double y) {
        return new Division(x, y).evaluate();
    }
}
