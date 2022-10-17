package com.iluwatar.sessionfacade;

import com.iluwatar.sessionfacade.session.OperationSessionBean;

/**
 * The Session Facade design pattern simplifies the management process and boost performance by
 * encapsulating business-tier components and expose a course-grained service access layer to
 * remote clients. It uses the Session bean as a facade to encapsulate the complexities of interaction
 * between business objects participating in the workflow.
 */

public class App {
    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>Service Activated<<<<<<<<<<");
        OperationSessionBean osb = new OperationSessionBean();
        double d1 = osb.add(11, 12);
        System.out.println("Result for 11 plus 12: " + d1);
        double d2 = osb.subtract(13, 12);
        System.out.println("Result for 13 minus 12: " + d2);
        double d3 = osb.multiply(15, 16);
        System.out.println("Result for 15 multiply 16: " + d3);
        double d4 = osb.divide(17, 34);
        System.out.println("Result for 17 divided by 34: " + d4);
        System.out.println(">>>>>>>>>>Service Terminated<<<<<<<<<<");
    }
}
